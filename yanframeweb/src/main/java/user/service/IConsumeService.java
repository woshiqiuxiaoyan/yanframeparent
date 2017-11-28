package user.service;

import user.dto.CtOrdersDTO;
import user.dto.SysUserDTO;

public interface IConsumeService {

    /**
     * 结算
     * @param sysUser
     * @param ctOrdersDTO
     * @return
     */
    String accountResultActive(SysUserDTO sysUser, CtOrdersDTO ctOrdersDTO);
}
