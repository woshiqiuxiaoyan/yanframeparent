package user.mapper;


import org.springframework.stereotype.Repository;
import pojo.SysUser;
import user.dto.*;

import java.util.List;

/**
 * Created by t on 2017/8/16.
 */
@Repository
public interface SysUserMapper {


    SysUserDTO querySysUserByPwdAndUserName(SysUser sysUser);

    /**
     * 用过user_id 返回 SysAutorityDTO 列表
     *
     * @param sysUser
     * @return
     */
    List<SysAuthorityDTO> getAutorityListByUserId(SysUserDTO sysUser);

    /**
     * 通过用户user_id 取角色
     *
     * @param sysUser
     * @return
     */
    SysRoleDTO getUserRole(SysUserDTO sysUser);

    /**
     * 获取系统用户列表
     *
     * @param sysUserDTO
     * @return
     */
    List<SysUserDTO> queryByCondition(SysUserDTO sysUserDTO);

    /**
     * 通过店铺ID取店铺信息
     *
     * @param sysStoreDTO
     * @return
     */
    SysStoreDTO queryStoreInfo(SysStoreDTO sysStoreDTO);

    /**
     * 查询用户角色 列表
     *
     * @param sysRoleDTO
     * @return
     */
    List<SysRoleDTO> querySysRoleByCondition(SysRoleDTO sysRoleDTO);



    /**
     * 获取按钮权限
     * @param sysUserDTO
     * @return
     */
    List<SysRolePermissionDTO> getRolePermissionByUserId(SysUserDTO sysUserDTO);

    int delSysUserByUserId(SysUserDTO sysUserDTO);

    /**
     * 通过手机号查询注册用户
     * @param user_phone
     * @return
     */
    SysUserDTO getSysuserByCheckPhone(String user_phone);

    /**
     * 新增系统用户
     * @param sysUserDTO
     * @return
     */
    int insertSysUser(SysUserDTO sysUserDTO);

    /**
     * 保存用户角色表
     * @param sysUserRoleDTO
     * @return
     */
    int saveSysUserRole(SysUserRoleDTO sysUserRoleDTO);

    /**
     * 系统角色查询权限
     * @param sysAuthorityDTO
     * @return
     */
    List<SysAuthorityDTO> queryPerssionByRoleId(SysAuthorityDTO sysAuthorityDTO);

    /**
    * 增加角色权限
     */
    int insertRolePerssion(SysAuthorityDTO sysAuthorityDTO);

    /**
     * 删除角色权限
     * @param sysAuthorityDTO
     * @return
     */
    int delRolePerssion(SysAuthorityDTO sysAuthorityDTO);

    /**
     * 增加角色
     * @param sysRoleDTO
     * @return
     */
    int addSysRole(SysRoleDTO sysRoleDTO);

    /**
     * 更新系统用户
     * @param sysUserDTO
     * @return
     */
    int updateSysUser(SysUserDTO sysUserDTO);

    /**
     * 更新用户角色
     * @param sysUserRoleDTO
     */
     int updateSysUserRole(SysUserRoleDTO sysUserRoleDTO);

    /**
     * 通过role_id 查询是否有用户正在使用这个角色
     * @param sysRoleDTO
     * @return
     */
    List<SysUserRoleDTO> querySysUserRole(SysRoleDTO sysRoleDTO);

    /**
     * 删除用户角色
     * @param sysRoleDTO
     * @return
     */
    int delSysRoleAndSysRoleAuthority(SysRoleDTO sysRoleDTO);
}
