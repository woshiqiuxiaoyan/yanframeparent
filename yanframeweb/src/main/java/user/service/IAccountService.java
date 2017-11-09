package user.service;

import com.github.pagehelper.Page;
import pojo.SysUser;
import user.dto.*;

import java.text.ParseException;
import java.util.List;

public interface IAccountService {


    /**
     * 登录
     * @param sysUser
     * @return
     */
    public SysUserDTO login(SysUserDTO sysUser);

    /**
     * 通用用户user_id取用户菜单信息列表
     * @param sysUser
     * @return
     */
    List<SysAuthorityDTO> getMenuList(SysUserDTO sysUser);

    /**
     * 获取当前用户角色
     * @param sysUser
     * @return
     */
    SysRoleDTO getUserRole(SysUserDTO sysUser);

    /**
     * 获取系统用户信息列表
     * @param sysUser
     * @param sysUserDTO
     * @return
     */
    Page<SysUserDTO> getSysUserInfoList(SysUserDTO sysUser, SysUserDTO sysUserDTO);

    /**
     * 查询角色列表
     * @param sysUser
     * @param sysRoleDTO
     * @return
     */
    Page<SysRoleDTO> getSysRoleList(SysUserDTO sysUser, SysRoleDTO sysRoleDTO);

    /**
     * 系统店铺列表
     * @param sysUser
     * @param sysStoreDTO
     * @return
     */
    Page<SysStoreDTO> getSysStoreList(SysUserDTO sysUser, SysStoreDTO sysStoreDTO);

    /**
     * 获取角色按钮权限
     * @param sysUserDTO
     * @return
     */
    List<SysRolePermissionDTO> getRolePermission(SysUserDTO sysUserDTO);

    /**
     * 删除用户
     * @param sysUserDTO
     * @return
     */
    int delSysUser(SysUserDTO sysUserDTO);

    /**
     * 增加系统用户
     * @param sysUser
     * @param sysUserDTO
     * @return
     */
    int addSysUser(SysUserDTO sysUser, SysUserDTO sysUserDTO) throws ParseException;

    /**
     * 不分页查询角色列表
     * @param sysUser
     * @param sysRoleDTO
     * @return
     */
    List<SysRoleDTO> getSysRoleListNoPage(SysUserDTO sysUser, SysRoleDTO sysRoleDTO);

    /**
     * 系统角色查询权限
     * @param sysUser
     * @param sysAuthorityDTO
     * @return
     */
    List<SysAuthorityDTO> queryPerssionByRoleId(SysUserDTO sysUser, SysAuthorityDTO sysAuthorityDTO);

    /**
     * 更新角色权限
     * @param sysAuthorityDTO
     * @return
     */
    int updateRolePerssion(SysAuthorityDTO sysAuthorityDTO);
}
