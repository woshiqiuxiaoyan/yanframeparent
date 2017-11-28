package user.mapper;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import user.dto.SysStockDTO;
import user.dto.SysStockLogDTO;
import user.dto.SysStockRecordDTO;

import java.util.List;

@Repository
public interface SysStockMapper {


    /**
     * 保存入库清单列表
     *
     * @param sysStockRecordDTOList
     * @return
     */
    int saveSysStockRecordDTOList(@Param("sysStockRecordDTOList") List<SysStockRecordDTO> sysStockRecordDTOList);

    /**
     * 通过清单批次查询清单列表
     * @param stock_record_id
     * @return
     */
    List<SysStockRecordDTO> queryByStockRecordId(String stock_record_id);

    /**
     * 更新库存
     * @param sysStockDTO
     * @return
     */
    int updateStockByGoodsInfoId(SysStockDTO sysStockDTO);

    /**
     * 插入库存
     * @param sysStockDTO
     * @return
     */
    int saveStock(SysStockDTO sysStockDTO);

    /**
     * 保存出入库履历
     * @param sysStockLogDTO
     * @return
     */
    int saveStockLog(SysStockLogDTO sysStockLogDTO);

    /**
     * 查询库存列表信息
     * @param sysStockDTO
     * @return
     */
    List<SysStockDTO> queryByCondition(SysStockDTO sysStockDTO);
}
