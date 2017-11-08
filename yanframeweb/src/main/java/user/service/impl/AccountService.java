package user.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import constant.Constant;
import constant.ErrorCode;
import enums.IsShopKeeper;
import exception.CustomException;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pojo.SysStore;
import pojo.SysUser;
import user.dto.*;
import user.mapper.SysUserMapper;
import user.mapper.TDemoMapper;
import user.service.IAccountService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class AccountService implements IAccountService {

    @Autowired
    private SysUserMapper sysUserMapper;



    public SysUserDTO login(SysUserDTO sysUser) {
        return  sysUserMapper.querySysUserByPwdAndUserName(sysUser);
    }

    /**
     * 获取当前用户菜单列表
     * @param sysUser
     * @return
     */
    public List<SysAuthorityDTO> getMenuList(SysUserDTO sysUser) {
        return sysUserMapper.getAutorityListByUserId(sysUser);
    }

    /**
     * 获取用户角色
     * @param sysUser
     * @return
     */
    public SysRoleDTO getUserRole(SysUserDTO sysUser) {
        return sysUserMapper.getUserRole(sysUser);
    }

    /**
     * 获取系统用户列表
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
     * @param sysUser
     * @param sysRoleDTO
     * @return
     */
    @Override
    public Page<SysRoleDTO> getSysRoleList(SysUserDTO sysUser, SysRoleDTO sysRoleDTO) {
        sysRoleDTO.setCreate_by_user_id(sysUser.getUser_id());

        if(sysUser.getUser_id().intValue()==1){
            //管理员 可查询所有角色
            sysRoleDTO.setCreate_by_user_id(null);
        }

        Page<SysRoleDTO> sysRoleDTOPage = PageHelper.startPage(sysRoleDTO.getPage(), sysRoleDTO.getLimit())
                .doSelectPage(() -> sysUserMapper.querySysRoleByCondition(sysRoleDTO));
        return sysRoleDTOPage;
    }

    /**
     * 查询店铺信息
     * @param sysUser
     * @param sysStoreDTO
     * @return
     */
    @Override
    public Page<SysStoreDTO> getSysStoreList(SysUserDTO sysUser, SysStoreDTO sysStoreDTO) {

        Page<SysStoreDTO> sysStoreDTOPage = PageHelper.startPage(sysStoreDTO.getPage(), sysStoreDTO.getLimit())
                .doSelectPage(() -> sysUserMapper.getSysStoreList(sysStoreDTO));
        return sysStoreDTOPage;
    }

    /**
     * 获取按钮权限
     * @param sysUserDTO
     * @return
     */
    @Override
    public List<SysRolePermissionDTO> getRolePermission(SysUserDTO sysUserDTO) {
        return  sysUserMapper.getRolePermissionByUserId(sysUserDTO);
    }

    /**
     * 删除系统用户
     * @param sysUserDTO
     * @return
     */
    @Override
    public int delSysUser(SysUserDTO sysUserDTO) {
        return sysUserMapper.delSysUserByUserId(sysUserDTO);
    }

    /**
     * 增加系统用户
     * @param sysUser
     * @param sysUserDTO
     * @return
     */
    @Override
    public int addSysUser(SysUserDTO sysUser, SysUserDTO sysUserDTO) throws ParseException {

        //对日期进行转化
        if(StringUtils.isNotBlank(sysUserDTO.getUser_birthday_form())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sysUserDTO.setUser_birthday(sdf.parse(sysUserDTO.getUser_birthday_form()));
        }

        sysUserDTO.setLogin_account(sysUserDTO.getUser_phone());//设置帐号为手机号
        //验证手机是否已经被注册过
        SysUserDTO tmp =  checkPhoneIsExist(sysUserDTO.getUser_phone());
        if(null!=tmp){
            throw new CustomException(ErrorCode.sys_user.USER_PHONE_IS_EXIST);
        }
        sysUserDTO.setCreate_by(sysUser.getUser_id());
        sysUserDTO.setStore_id(sysUser.getStore_id());//设置当前店铺
        sysUserDTO.setIs_shop_keeper(IsShopKeeper.STAFF.getValue());//默认普通员工
        sysUserDTO.setLogin_pass(Constant.sys_user.DEFAULT_PASSWORD);//设置初始密码
        return   sysUserMapper.insertSysUser(sysUserDTO);
    }

    /**
     * 不分页查询角色列表
     * @param sysUser
     * @param sysRoleDTO
     * @return
     */
    @Override
    public List<SysRoleDTO> getSysRoleListNoPage(SysUserDTO sysUser, SysRoleDTO sysRoleDTO) {

        sysRoleDTO.setCreate_by_user_id(sysUser.getUser_id());

        if(sysUser.getUser_id().intValue()==1){
            //管理员 可查询所有角色
            sysRoleDTO.setCreate_by_user_id(null);
        }

        return sysUserMapper.querySysRoleByCondition(sysRoleDTO);
    }


    /**
     * 验证手机对应的用户是否存在
     * @param user_phone
     * @return
     */
    public SysUserDTO checkPhoneIsExist(String user_phone){
        return sysUserMapper.getSysuserByCheckPhone(user_phone);
    }

}
