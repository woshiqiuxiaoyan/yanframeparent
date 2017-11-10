package user.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sun.tools.internal.jxc.ap.Const;
import constant.Constant;
import constant.ErrorCode;
import enums.IsShopKeeper;
import exception.CustomException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import user.dto.*;
import user.mapper.SysUserMapper;
import user.service.IAccountService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Title:AccountService </p>
 * <p>Description:</p>
 * Created with IntelliJ IDEA.
 * User: qxy
 * Date: 2017/10/9
 * Time: 9:54
 */
@Service
public class AccountService implements IAccountService {

    @Autowired
    private SysUserMapper sysUserMapper;



    private Logger log = LoggerFactory.getLogger(AccountService.class);


    public SysUserDTO login(SysUserDTO sysUser) {
        return sysUserMapper.querySysUserByPwdAndUserName(sysUser);
    }

    /**
     * 获取当前用户菜单列表
     *
     * @param sysUser
     * @return
     */
    public List<SysAuthorityDTO> getMenuList(SysUserDTO sysUser) {
        return sysUserMapper.getAutorityListByUserId(sysUser);
    }

    /**
     * 获取用户角色
     *
     * @param sysUser
     * @return
     */
    public SysRoleDTO getUserRole(SysUserDTO sysUser) {
        return sysUserMapper.getUserRole(sysUser);
    }

    /**
     * 获取系统用户列表
     *
     * @param sysUser
     * @param sysUserDTO
     * @return
     */
    @Override
    public Page<SysUserDTO> getSysUserInfoList(SysUserDTO sysUser, SysUserDTO sysUserDTO) {
        if (null == sysUser || null == sysUser.getUser_id()) {
            //未登录异常
            throw new CustomException(ErrorCode.sys_user.NO_LOGIN_ERROR);
        }

        sysUserDTO.setStore_id(sysUser.getStore_id());

        Page<SysUserDTO> sysUserDTOPage = PageHelper.startPage(sysUserDTO.getPage(), sysUserDTO.getLimit())
                .doSelectPage(() -> sysUserMapper.queryByCondition(sysUserDTO));


        return sysUserDTOPage;

    }

    /**
     * 查询角色列表
     *
     * @param sysUser
     * @param sysRoleDTO
     * @return
     */
    @Override
    public Page<SysRoleDTO> getSysRoleList(SysUserDTO sysUser, SysRoleDTO sysRoleDTO) {

        //普通员工只能看到自己创建的角色
        sysRoleDTO.setCreate_by_user_id(sysUser.getUser_id());
        if (sysUser.getUser_id().intValue() == 1) {
            //管理员 可查询所有角色
            sysRoleDTO.setCreate_by_user_id(null);
        }
        Page<SysRoleDTO> sysRoleDTOPage = PageHelper.startPage(sysRoleDTO.getPage(), sysRoleDTO.getLimit())
                .doSelectPage(() -> sysUserMapper.querySysRoleByCondition(sysRoleDTO));
        return sysRoleDTOPage;
    }



    /**
     * 获取按钮权限
     *
     * @param sysUserDTO
     * @return
     */
    @Override
    public List<SysRolePermissionDTO> getRolePermission(SysUserDTO sysUserDTO) {
        return sysUserMapper.getRolePermissionByUserId(sysUserDTO);
    }

    /**
     * 删除系统用户
     *
     * @param sysUserDTO
     * @return
     */
    @Override
    public int delSysUser(SysUserDTO sysUserDTO) {
        return sysUserMapper.delSysUserByUserId(sysUserDTO);
    }

    /**
     * 增加系统用户
     *
     * @param sysUser
     * @param sysUserDTO
     * @return
     */

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int addSysUser(SysUserDTO sysUser, SysUserDTO sysUserDTO) throws ParseException {

        if (sysUserDTO.getRole_id() == null) {
            throw new CustomException(Constant.sys_user.ADD_SYSUSER_FAIL);
        }

        //验证手机是否已经被注册过
        SysUserDTO tmp = checkPhoneIsExist(sysUserDTO.getUser_phone());
        if (null != tmp) {
            throw new CustomException(ErrorCode.sys_user.USER_PHONE_IS_EXIST);
        }

        //保存sysUser
        saveSysUserMethod(sysUser, sysUserDTO);

        //保存用户角色sys_user_role
        saveSysUserRoleMethod(sysUserDTO);

        return 1;
    }

    /**
     * 保存用户角色
     *
     * @param sysUserDTO
     * @return
     */
    private int saveSysUserRoleMethod(SysUserDTO sysUserDTO) {
        SysUserRoleDTO sysUserRoleDTO = new SysUserRoleDTO();
        sysUserRoleDTO.setRole_id(sysUserDTO.getRole_id());
        sysUserRoleDTO.setUser_id(sysUserDTO.getUser_id());

        int effect = sysUserMapper.saveSysUserRole(sysUserRoleDTO);

        if (effect == 0) {
            throw new CustomException(Constant.sys_user.ADD_SYSUSER_FAIL);
        }
        return effect;
    }

    /**
     * 保存sysUser
     *
     * @param sysUser
     * @param sysUserDTO
     * @return
     * @throws ParseException
     */
    private int saveSysUserMethod(SysUserDTO sysUser, SysUserDTO sysUserDTO) throws ParseException {
        //对日期进行转化
        if (StringUtils.isNotBlank(sysUserDTO.getUser_birthday_form())) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sysUserDTO.setUser_birthday(sdf.parse(sysUserDTO.getUser_birthday_form()));
        }

        sysUserDTO.setLogin_account(sysUserDTO.getUser_phone());//设置帐号为手机号
        sysUserDTO.setCreate_by(sysUser.getUser_id());
        sysUserDTO.setStore_id(sysUser.getStore_id());//设置当前店铺
        sysUserDTO.setIs_shop_keeper(IsShopKeeper.STAFF.getValue());//默认普通员工
        sysUserDTO.setLogin_pass(Constant.sys_user.DEFAULT_PASSWORD);//设置初始密码
        int effect = sysUserMapper.insertSysUser(sysUserDTO);

        if (effect == 0) {
            throw new CustomException(Constant.sys_user.ADD_SYSUSER_FAIL);
        }
        return effect;
    }


    /**
     * 不分页查询角色列表
     *
     * @param sysUser
     * @param sysRoleDTO
     * @return
     */
    @Override
    public List<SysRoleDTO> getSysRoleListNoPage(SysUserDTO sysUser, SysRoleDTO sysRoleDTO) {

        sysRoleDTO.setCreate_by_user_id(sysUser.getUser_id());

        if (sysUser.getUser_id().intValue() == 1) {
            //管理员 可查询所有角色
            sysRoleDTO.setCreate_by_user_id(null);
        }

        return sysUserMapper.querySysRoleByCondition(sysRoleDTO);
    }

    /**
     * 系统角色查询权限
     *
     * @param sysUser
     * @param sysAuthorityDTO
     * @return
     */
    @Override
    public List<SysAuthorityDTO> queryPerssionByRoleId(SysUserDTO sysUser, SysAuthorityDTO sysAuthorityDTO) {

        // 当前选中用户的权限
        List<SysAuthorityDTO> sysAuthoriTOListCur = sysUserMapper.queryPerssionByRoleId(sysAuthorityDTO);


        // 当前用户的权限
        List<SysAuthorityDTO> sysAuthorityDTOList = this.getMenuList(sysUser);

        sysAuthorityDTOList.stream().forEach(
                (string) -> {
                    string.setRole_id(sysAuthorityDTO.getRole_id());
                    string.setChecked(false);
                    if(sysAuthoriTOListCur!=null && sysAuthoriTOListCur.size()>0){
                        List tmp = sysAuthoriTOListCur.stream().filter((cur) -> string.getId().intValue() == cur.getId().intValue()).collect(Collectors.toList());
                        if (tmp != null && tmp.size() > 0) {
                            string.setChecked(true);
                        }
                    }
                }
        );
        return sysAuthorityDTOList;
    }

    /**
     * 更新角色权限
     *
     * @param sysAuthorityDTO
     * @return
     */
    @Override
    public int updateRolePerssion(SysAuthorityDTO sysAuthorityDTO) {

        int effect = 0;
        if (sysAuthorityDTO.isChecked()) {
            effect = sysUserMapper.insertRolePerssion(sysAuthorityDTO);
        } else {
            effect = sysUserMapper.delRolePerssion(sysAuthorityDTO);
        }

        return effect;
    }

    @Override
    public int addSysRole(SysUserDTO sysUser, SysRoleDTO sysRoleDTO) {
        sysRoleDTO.setCreate_by_user_id(sysUser.getUser_id());
        int effect = sysUserMapper.addSysRole(sysRoleDTO);
        if(effect==0){
            throw new CustomException(Constant.sys_user.ADD_ROLE_FAIL);
        }
        return effect;
    }

    /**
     * 更新系统用户
     * @param sysUserDTO
     * @return
     */
    @Override
    public int updateSysUser(SysUserDTO sysUserDTO) throws ParseException {
        if(null==sysUserDTO.getUser_id()){
            throw new CustomException(Constant.sys_user.UPDATE_ROLE_FAIL);
        }


        //对日期进行转化
        if (StringUtils.isNotBlank(sysUserDTO.getUser_birthday_form())) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sysUserDTO.setUser_birthday(sdf.parse(sysUserDTO.getUser_birthday_form()));
        }

        log.info("----------------------更新系统用户------------------------------------");
        sysUserMapper.updateSysUser(sysUserDTO);

        SysUserRoleDTO sysUserRoleDTO = new SysUserRoleDTO();
        sysUserRoleDTO.setRole_id(sysUserDTO.getRole_id());
        sysUserRoleDTO.setUser_id(sysUserDTO.getUser_id());
        sysUserMapper.updateSysUserRole(sysUserRoleDTO);


        return 1;
    }

    /**
     * 删除角色
     * @param sysRoleDTO
     * @return
     */
    @Override
    public int delSysRole(SysRoleDTO sysRoleDTO) {

        checkRoleIsUsed(sysRoleDTO);

        //删除角色
        int effect = sysUserMapper.delSysRoleAndSysRoleAuthority(sysRoleDTO);

        if(effect==0){
            throw  new CustomException(Constant.sys_user.DEL_ROLE_FAIL);
        }
        return effect;
    }

    /**
     * 查询该角色是否正在被使用
     * @param sysRoleDTO
     * @return
     */
    private boolean checkRoleIsUsed(SysRoleDTO sysRoleDTO) {

        List<SysUserRoleDTO> sysUserRoleDTOList =  sysUserMapper.querySysUserRole(sysRoleDTO);

        if(null!=sysUserRoleDTOList&&sysUserRoleDTOList.size()>0){
            throw new CustomException(Constant.sys_user.ROLE_IS_USERING);
        }
        return false;
    }


    /**
     * 验证手机对应的用户是否存在
     *
     * @param user_phone
     * @return
     */
    public SysUserDTO checkPhoneIsExist(String user_phone) {
        return sysUserMapper.getSysuserByCheckPhone(user_phone);
    }

}
