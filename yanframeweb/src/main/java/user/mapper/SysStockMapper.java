package user.mapper;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
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
}
