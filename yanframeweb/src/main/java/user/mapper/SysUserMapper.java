package user.mapper;


import org.springframework.stereotype.Repository;
import pojo.SysUser;
import user.dto.SysAuthorityDTO;
import user.dto.SysRoleDTO;
import user.dto.SysStoreDTO;
import user.dto.SysUserDTO;

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
     * 查询店铺列表
     * @param sysStoreDTO
     * @return
     */
    List<SysStoreDTO> getSysStoreList(SysStoreDTO sysStoreDTO);
}
