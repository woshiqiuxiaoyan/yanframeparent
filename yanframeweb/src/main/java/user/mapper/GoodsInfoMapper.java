package user.mapper;

import org.springframework.stereotype.Repository;
import user.dto.CtUserInfoDTO;
import user.dto.SysGoodsInfoDTO;

import java.util.List;

@Repository
public interface GoodsInfoMapper {

    /**
     * 保存商品
     * @param sysGoodsInfoDTO
     * @return
     */
    int saveGoodsInfo(SysGoodsInfoDTO sysGoodsInfoDTO);

    /**
     * 根据条件查询商品列表
     * @param sysGoodsInfoDTO
     */
    List<SysGoodsInfoDTO> queryByCondition(SysGoodsInfoDTO sysGoodsInfoDTO);

    /**
     * 通过id删除产品
     * @param sysGoodsInfoDTO
     * @return
     */
    int delSysGoodsInfoById(SysGoodsInfoDTO sysGoodsInfoDTO);

    /**
     * 更新产品
     * @param sysGoodsInfoDTO
     * @return
     */
    int updateGoodsInfoById(SysGoodsInfoDTO sysGoodsInfoDTO);

    /**
     * 通过id取产品信息
     * @param sysGoodsInfoDTO
     * @return
     */
    SysGoodsInfoDTO queryById(SysGoodsInfoDTO sysGoodsInfoDTO);
}
