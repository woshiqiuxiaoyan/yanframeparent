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
import user.dto.CtUserGradeDTO;
import user.dto.CtUserInfoDTO;
import user.dto.SysRoleDTO;
import user.dto.SysUserDTO;
import user.mapper.CtUserInfoMapper;
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

        if (StringUtils.isBlank(ctUserInfoDTO.getReal_name())) {
            throw new CustomException(ErrorCode.create_card.REAL_NAME_ERROR);
        }


        //判断卡号是否已经存在

        CtUserInfoDTO tmp = ctUserInfoMapper.queryCtUserInfoByCardNo(ctUserInfoDTO.getCard_no());

        if (tmp != null) {
            throw new CustomException(ErrorCode.create_card.CARD_USERD_ERROR);
        }




        //设置经办人
        ctUserInfoDTO.setUser_id(sysUser.getUser_id());
        //设置店铺
        ctUserInfoDTO.setStore_id(sysUser.getStore_id());

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
     *
     * @param sysUser
     * @param ctUserInfoDTO
     * @return
     */
    public Page<CtUserInfoDTO> ctuserList(SysUserDTO sysUser, CtUserInfoDTO ctUserInfoDTO) {

        if (null == sysUser || null == sysUser.getUser_id()) {
            //未登录异常
            throw new CustomException(ErrorCode.sys_user.NO_LOGIN_ERROR);
        }


        ctUserInfoDTO.setStore_id(sysUser.getStore_id());

        Page<CtUserInfoDTO> list = PageHelper.startPage(ctUserInfoDTO.getPage(), ctUserInfoDTO.getLimit())
                .doSelectPage(() -> ctUserInfoMapper.queryByCondition(ctUserInfoDTO));


        //查询会员等级
        List<CtUserGradeDTO> ctUserGradeDTOList = ctUserInfoMapper.queryCtUserGradeList(new CtUserGradeDTO());


        list.stream().forEach(
                (string) -> {
                    ctUserGradeDTOList.stream().forEach((tmp1) -> {
                        if (tmp1.getAccumulate_integral().intValue() <= string.getAccumulate_integral()) {
                            string.setGrade_name(tmp1.getGrade_name());
                        }
                    });
                }
        );


        list.forEach(System.out::println);


        return list;
    }


    @Override
    public int updateCtUserInfo(SysUserDTO sysUser, CtUserInfoDTO ctUserInfoDTO) throws CustomException {

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

        if (effect == 0) {
            throw new CustomException(ErrorCode.create_card.CARD_USER_UPDATE_FAIL);
        }

        return 1;
    }

    /**
     * 删除会员
     *
     * @param ctUserInfoDTO
     * @return
     */
    @Override
    public int delCtuser(CtUserInfoDTO ctUserInfoDTO) {
        if (ctUserInfoDTO.getId() == null) {
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

    /**
     * 获取会员等级列表
     *
     * @param ctUserGradeDTO
     * @return
     */
    @Override
    public Page<CtUserGradeDTO> getCtUserGradeList(CtUserGradeDTO ctUserGradeDTO) {

        Page<CtUserGradeDTO> list = PageHelper.startPage(ctUserGradeDTO.getPage(), ctUserGradeDTO.getLimit())
                .doSelectPage(() -> ctUserInfoMapper.queryCtUserGradeList(ctUserGradeDTO));

        return list;
    }

    @Override
    public int addCtUserGrade(CtUserGradeDTO ctUserGradeDTO) {
        return ctUserInfoMapper.insertCtUserGrade(ctUserGradeDTO);
    }

    @Override
    public int updateCtUserGrade(SysUserDTO sysUser, CtUserGradeDTO ctUserGradeDTO) {
        return ctUserInfoMapper.updateCtUserGradeById(ctUserGradeDTO);
    }

    @Override
    public int delCtUserGrade(Integer id) {
        return ctUserInfoMapper.delCtUserGrade(id);
    }
}
