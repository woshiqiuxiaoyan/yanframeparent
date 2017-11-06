package user.service;

import com.github.pagehelper.Page;
import pojo.SysUser;
import user.dto.SysAuthorityDTO;
import user.dto.SysRoleDTO;
import user.dto.SysStoreDTO;
import user.dto.SysUserDTO;

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
}
