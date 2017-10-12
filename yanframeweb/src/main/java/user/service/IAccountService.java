package user.service;

import pojo.SysUser;
import user.dto.SysAuthorityDTO;
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
}
