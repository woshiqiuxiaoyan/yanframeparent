package user.service.impl;

import constant.ErrorCode;
import enums.StockRecordType;
import exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import user.dto.SysGoodsInfoDTO;
import user.dto.SysStockDTO;
import user.dto.SysStockRecordDTO;
import user.dto.SysUserDTO;
import user.mapper.GoodsInfoMapper;
import user.mapper.SysStockMapper;
import user.service.ISysStockService;
import utils.CommonTools;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author qxy
 * @Date: 2017/10/30 10:00
 */
@Service
public class SysStockServiceImpl implements ISysStockService {


    @Autowired
    private SysStockMapper sysStockMapper;

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;


    private Logger log = LoggerFactory.getLogger(SysStockServiceImpl.class);

    /**
     * 进货
     *
     * @param sysUser
     * @param sysStockRecordDTOS
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String inStock(SysUserDTO sysUser, SysStockRecordDTO[] sysStockRecordDTOS) {
        if (null == sysUser || null == sysUser.getUser_id()) {
            //未登录异常
            throw new CustomException(ErrorCode.sys_user.NO_LOGIN_ERROR);
        }

        if (null == sysStockRecordDTOS || sysStockRecordDTOS.length == 0) {
            throw new CustomException(ErrorCode.manager_Stock.INSTOCK_FAIL);
        }

        log.info("------------------------------查询商品列表------------------------------");
        List<SysGoodsInfoDTO> sysGoodsInfoDTOList = getGoodsInfosById(sysStockRecordDTOS);

        log.info("------------------------------保存库存清单------------------------------");
        String stock_record_id = saveStockRecordList(sysUser, sysStockRecordDTOS);

        log.info("------------------------------保存库存------------------------------");




        return stock_record_id;
    }

    /**
     * 保存入库清单列表
     * @param sysUser
     * @param sysStockRecordDTOS
     * @return
     */
    private String saveStockRecordList(SysUserDTO sysUser, SysStockRecordDTO[] sysStockRecordDTOS) {

        String stock_record_id= CommonTools.createRandomString();

        List<SysStockRecordDTO> sysStockRecordDTOList1 =   sysStockMapper.queryByStockRecordId(stock_record_id);

        sysStockRecordDTOList1.forEach(System.out::println);


        //生成订单批次/
        List<SysStockRecordDTO> sysStockRecordDTOList = Stream.of(sysStockRecordDTOS).filter((string) -> string.getGoods_info_id() != null).map((tmp) -> {
            tmp.setStock_record_id(stock_record_id);//设置批次
            tmp.setType(StockRecordType.INSTOCK.getValue());//清单类型
            tmp.setCreate_by(sysUser.getUser_id());
            return tmp;
        }).collect(Collectors.toList());


        int effect =    sysStockMapper.saveSysStockRecordDTOList(sysStockRecordDTOList);

        if(effect!=sysStockRecordDTOS.length){
            throw new CustomException(ErrorCode.manager_Stock.INSTOCK_FAIL);
        }
        return stock_record_id;
    }

    /**
     * 通过商品ID列表查询商品信息列表
     *
     * @param sysStockRecordDTOS
     */
    private List<SysGoodsInfoDTO> getGoodsInfosById(SysStockRecordDTO[] sysStockRecordDTOS) {
        //将商品id放入idList中
        List<SysGoodsInfoDTO> idList = Stream.of(sysStockRecordDTOS).filter((string) -> string.getGoods_info_id() != null).map((tmp) -> {
            SysGoodsInfoDTO sysGoodsInfoDTO = new SysGoodsInfoDTO();
            sysGoodsInfoDTO.setId(tmp.getGoods_info_id());
            return sysGoodsInfoDTO;
        }).collect(Collectors.toList());

        idList = goodsInfoMapper.queryByIdList(idList);

        if (null == idList || sysStockRecordDTOS.length > idList.size()) {
            throw new CustomException(ErrorCode.manager_Stock.INSTOCK_FAIL);
        }
        return idList;
    }
}
