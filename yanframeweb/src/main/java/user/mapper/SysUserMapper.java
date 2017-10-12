package user.mapper;


import org.springframework.stereotype.Repository;
import pojo.SysUser;
import user.dto.SysAuthorityDTO;
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
     * @param sysUser
     * @return
     */
    List<SysAuthorityDTO> getAutorityListByUserId(SysUserDTO sysUser);
}
