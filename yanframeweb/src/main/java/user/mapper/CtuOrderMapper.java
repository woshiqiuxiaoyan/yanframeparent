package user.mapper;


import org.springframework.stereotype.Repository;
import user.dto.CtOrdersDTO;

@Repository
public interface CtuOrderMapper {

    /**
     * 插入订单
     * @param ctOrdersDTO
     * @return
     */
    int insertCtOrder(CtOrdersDTO ctOrdersDTO);
}
