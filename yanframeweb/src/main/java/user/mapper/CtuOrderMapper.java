package user.mapper;


import org.springframework.stereotype.Repository;
import user.dto.CtOrderDetailDTO;
import user.dto.CtOrdersDTO;

import java.util.List;

@Repository
public interface CtuOrderMapper {

    /**
     * 插入订单
     * @param ctOrdersDTO
     * @return
     */
    int insertCtOrder(CtOrdersDTO ctOrdersDTO);

    /**
     * 插入订单详情
     * @param ctOrderDetailDTOTmp
     * @return
     */
    int insertOrderDetail(CtOrderDetailDTO ctOrderDetailDTOTmp);

    /**
     * 查询订单列表
     * @param ctOrdersDTO
     * @return
     */
    List<CtOrdersDTO> queryOrder(CtOrdersDTO ctOrdersDTO);

    /**
     * 取订单详情列表
     * @param ctOrdersDTO
     * @return
     */
    List<CtOrderDetailDTO> queryOrderDetail(CtOrdersDTO ctOrdersDTO);
}
