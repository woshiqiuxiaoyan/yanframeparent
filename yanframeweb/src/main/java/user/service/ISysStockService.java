package user.service;

import com.github.pagehelper.Page;
import user.dto.SysStockDTO;
import user.dto.SysStockRecordDTO;
import user.dto.SysUserDTO;

public interface ISysStockService  {

    /**
     * 进货
     * @param sysUserDTO
     * @param sysStockRecordDTOS
     * @return
     */
    String inStock(SysUserDTO sysUserDTO, SysStockRecordDTO[] sysStockRecordDTOS);

    /**
     * 库存列表
     * @param sysUser
     * @param sysStockDTO
     * @return
     */
    Page<SysStockDTO> getStockInfoList(SysUserDTO sysUser, SysStockDTO sysStockDTO);
}
