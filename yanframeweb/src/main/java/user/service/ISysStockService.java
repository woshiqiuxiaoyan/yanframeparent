package user.service;

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

}
