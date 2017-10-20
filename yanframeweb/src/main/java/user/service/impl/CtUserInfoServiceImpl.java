package user.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import constant.Constant;
import constant.ErrorCode;
import exception.CustomException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pojo.SysRole;
import user.dto.CtUserInfoDTO;
import user.dto.SysRoleDTO;
import user.dto.SysUserDTO;
import user.mapper.CtUserInfoMapper;
import user.mapper.SysUserMapper;
import user.service.IAccountService;
import user.service.ICtUserInfoService;

import java.util.List;

/**
 * <p>Title:AccountService </p>
 * <p>Description:</p>
 * Created with IntelliJ IDEA.
 * User: qxy
 * Date: 2017/10/9
 * Time: 9:54
 */
@Service
public class CtUserInfoServiceImpl implements ICtUserInfoService {

    @Autowired
    private IAccountService iAccountService;

    @Autowired
    private CtUserInfoMapper ctUserInfoMapper;

    /**
     * @param sysUser
     * @param ctUserInfoDTO
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public int creatCard(SysUserDTO sysUser, CtUserInfoDTO ctUserInfoDTO) throws CustomException {

        if (null == sysUser || null == sysUser.getUser_id()) {
            //未登录异常
            throw new CustomException(ErrorCode.sys_user.NO_LOGIN_ERROR);
        }

        if (StringUtils.isBlank(ctUserInfoDTO.getCard_no())) {
            throw new CustomException(ErrorCode.sys_user.CARD_NO_ERROR);
        }

        if (StringUtils.isBlank( ctUserInfoDTO.getReal_name())) {
            throw new CustomException(ErrorCode.create_card.REAL_NAME_ERROR);
        }

        //判断卡号是否已经存在

        CtUserInfoDTO tmp = ctUserInfoMapper.queryCtUserInfoByCardNo(ctUserInfoDTO.getCard_no());

        if (tmp != null) {
            throw new CustomException(ErrorCode.create_card.CARD_USERD_ERROR);
        }


        if (isShopKeeper(sysUser)) {
            //店长 或者 管理员 将自己设置成为会员所属店铺
            ctUserInfoDTO.setShop_keeper_user_id(sysUser.getUser_id());
        } else {
            ctUserInfoDTO.setShop_keeper_user_id(sysUser.getCreate_by());
        }

        //设置经办人
        ctUserInfoDTO.setUser_id(sysUser.getUser_id());

        //对推荐人卡号进行判断 是否输入正确

        if (StringUtils.isNotBlank(ctUserInfoDTO.getReferee_card_no())) {
            tmp = ctUserInfoMapper.queryCtUserInfoByCardNo(ctUserInfoDTO.getReferee_card_no());
            if (tmp == null) {
                throw new CustomException(ErrorCode.create_card.REFEREE_CARD_NO_EXIST);
            }
        }


        ctUserInfoDTO.setRemark(Constant.NorMsg.CREATECARDREMARK);


        int effect = ctUserInfoMapper.saveCtUserInfo(ctUserInfoDTO);

        if (effect == 0) {
            throw new CustomException(ErrorCode.sys_user.CREATE_CARD_ERROR);
        }

        return effect;
    }

    /**
     * 查询会员列表
     * @param sysUser
     * @param ctUserInfoDTO
     * @return
     */
    public Page<CtUserInfoDTO> ctuserList(SysUserDTO sysUser, CtUserInfoDTO ctUserInfoDTO) {

        if (null == sysUser || null == sysUser.getUser_id()) {
            //未登录异常
            throw new CustomException(ErrorCode.sys_user.NO_LOGIN_ERROR);
        }

        //查询当前用店长（店铺）的会员列表
        if (isShopKeeper(sysUser)) {
            //当前 店长 或者 管理员登录则 将自己设置成为会员所属店铺
            ctUserInfoDTO.setShop_keeper_user_id(sysUser.getUser_id());
        } else {
            //普通员工取自己的创建者（店长）
            ctUserInfoDTO.setShop_keeper_user_id(sysUser.getCreate_by());
        }

        Page<CtUserInfoDTO> list =  PageHelper.startPage(ctUserInfoDTO.getPage(), ctUserInfoDTO.getLimit())
                .doSelectPage(() -> ctUserInfoMapper.queryByCondition(ctUserInfoDTO));

        return list;
    }


    @Override
    public int updateCtUserInfo(SysUserDTO sysUser, CtUserInfoDTO ctUserInfoDTO) throws CustomException{

        LoggerFactory.getLogger(this.getClass()).info("----------------更新会员---------------------");

        //对推荐人卡号进行判断 是否输入正确

        if (StringUtils.isNotBlank(ctUserInfoDTO.getReferee_card_no())) {
            CtUserInfoDTO tmp = ctUserInfoMapper.queryCtUserInfoByCardNo(ctUserInfoDTO.getReferee_card_no());
            if (tmp == null) {
                throw new CustomException(ErrorCode.create_card.REFEREE_CARD_NO_EXIST);
            }
        }


        //更新操作人
        ctUserInfoDTO.setUser_id(sysUser.getUser_id());

        int effect = ctUserInfoMapper.updateCtUserInfo(ctUserInfoDTO);

        if(effect==0){
            throw new CustomException(ErrorCode.create_card.CARD_USER_UPDATE_FAIL);
        }

        return 1;
    }

    /**
     * 删除会员
     * @param ctUserInfoDTO
     * @return
     */
    @Override
    public int delCtuser(CtUserInfoDTO ctUserInfoDTO) {
        if(ctUserInfoDTO.getId()==null){
            throw new CustomException(ErrorCode.sys_error.PARAM_FAIL);
        }
        return ctUserInfoMapper.delCtuser(ctUserInfoDTO);
    }


    /**
     * 判断当前用户是不是店长或者管理员
     *
     * @return
     */
    public boolean isShopKeeper(SysUserDTO sysUser) {
        //取用户角色
        SysRoleDTO sysRoleDTO = iAccountService.getUserRole(sysUser);
        if (null != sysRoleDTO) {
            if (sysRoleDTO.getRole_key().equals("2") || sysRoleDTO.getRole_key().equals("1")) {
                return true;
            }
        } else {
            throw new CustomException(ErrorCode.sys_user.SYS_ROLE_ERROR);
        }

        return false;

    }
}
