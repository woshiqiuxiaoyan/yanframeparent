package user.service;

import com.github.pagehelper.Page;
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

    /**
     * 查询订单列表
     * @param sysUser
     * @param ctOrdersDTO
     * @return
     */
    Page<CtOrdersDTO> getOrdersList(SysUserDTO sysUser, CtOrdersDTO ctOrdersDTO);

    /**
     * 查询订单详情
     * @param sysUser
     * @param ctOrdersDTO
     * @return
     */
    CtOrdersDTO getOrdersDetail(SysUserDTO sysUser, CtOrdersDTO ctOrdersDTO);
}
