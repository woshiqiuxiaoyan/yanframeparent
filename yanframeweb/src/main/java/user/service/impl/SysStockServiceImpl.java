package user.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import constant.Constant;
import constant.ErrorCode;
import enums.StockRecordType;
import enums.StockType;
import exception.CustomException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import user.dto.*;
import user.mapper.GoodsInfoMapper;
import user.mapper.SysStockMapper;
import user.service.ICtUserInfoService;
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

    @Autowired
    private ICtUserInfoService ctUserInfoService;


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

        //过滤为数量为0的商品
        List<SysStockRecordDTO> sysStockRecordDTOList = Stream.of(sysStockRecordDTOS).filter(
                (string) -> string.getNum().intValue() > 0).collect(Collectors.toList());

        if (null == sysStockRecordDTOList || sysStockRecordDTOList.size() == 0) {
            throw new CustomException(ErrorCode.manager_Stock.INSTOCK_GOODS_NUM_FAIL);
        }


        log.info("------------------------------查询商品列表------------------------------");
        List<SysGoodsInfoDTO> sysGoodsInfoDTOList = getGoodsInfosById(sysStockRecordDTOList);

        log.info("------------------------------保存库存清单------------------------------");
        String stock_record_id = saveStockRecordList(sysUser, sysStockRecordDTOList);

        log.info("------------------------------保存入库------------------------------");
        return saveAboutStockInfo(sysUser, sysStockRecordDTOList, sysGoodsInfoDTOList, stock_record_id);
    }


    /**
     * 取库存列表
     *
     * @param sysUser
     * @param sysStockDTO
     * @return
     */
    @Override
    public Page<SysStockDTO> getStockInfoList(SysUserDTO sysUser, SysStockDTO sysStockDTO) {

        //取店铺ID
        sysStockDTO.setStore_id(sysUser.getStore_id());

        Page<SysStockDTO> list = PageHelper.startPage(sysStockDTO.getPage(), sysStockDTO.getLimit())
                .doSelectPage(() -> sysStockMapper.queryByCondition(sysStockDTO));

        list.stream().map(string -> {
            String[] img_url_show = GoodsInfoServiceImpl.setImgurl(string.getGoods_img_url());
            string.setImg_url_show(img_url_show);
            return string;
        }).collect(Collectors.toList());

        return list;
    }

    /**
     * 保存入库相关信息
     *
     * @param sysUser
     * @param sysStockRecordDTOS
     * @param sysGoodsInfoDTOList
     */
    private String saveAboutStockInfo(SysUserDTO sysUser, List<SysStockRecordDTO> sysStockRecordDTOS, List<SysGoodsInfoDTO> sysGoodsInfoDTOList, String stock_record_id) {
        sysStockRecordDTOS.stream().forEach((string) -> {

            if (sysUser.getStore_id() == null) {
                throw new CustomException(ErrorCode.sys_user.NO_LOGIN_ERROR);
            }

            SysStockDTO sysStockDTO = new SysStockDTO();
            sysStockDTO.setGoods_info_id(string.getGoods_info_id());
            sysStockDTO.setNum(string.getNum());
            sysStockDTO.setStore_id(sysUser.getStore_id()); //设置店铺
            sysStockDTO.setRemark("进货");//备注
            //如果商品已经存在则更新库存
            int effect = sysStockMapper.updateStockNum(sysStockDTO);

            if (0 == effect) {
                //新增库存
                sysStockDTO.setTotal(string.getNum());
                effect = sysStockMapper.saveStock(sysStockDTO);
                if (0 == effect) {
                    throw new CustomException(ErrorCode.manager_Stock.INSTOCK_FAIL);
                }
            }



            log.info("------------------------------保存库存履历------------------------------");
            //取商品入库价格
            List<SysGoodsInfoDTO> list = sysGoodsInfoDTOList.stream().filter(
                    (sysGoodsInfoDTOTmp) -> sysGoodsInfoDTOTmp.getId().equals(string.getGoods_info_id()))
                    .collect(Collectors.toList());

            Integer goods_instock_price = list.get(0).getGoods_instock_price();

            saveSysStockLog(sysStockDTO.getId(), sysStockDTO.getRemark(), sysUser.getUser_id(), string.getNum(), goods_instock_price, stock_record_id);

        });
        return stock_record_id;
    }

    /**
     * 保存库存履历
     *
     * @param stock_id
     * @param remark
     * @param user_id
     * @param num
     * @param goods_instock_price
     * @param stock_record_id
     * @return
     */
    private int saveSysStockLog(Integer stock_id, String remark, Integer user_id, Integer num, double goods_instock_price, String stock_record_id) {
        SysStockLogDTO sysStockLogDTO = new SysStockLogDTO();

        sysStockLogDTO.setStock_id(stock_id);

        sysStockLogDTO.setType(StockType.ADDSTOCK.getValue());

        sysStockLogDTO.setOperator_num(num);

        sysStockLogDTO.setCreate_by(user_id);

        sysStockLogDTO.setRemark(remark);

        sysStockLogDTO.setPrice(goods_instock_price);

        sysStockLogDTO.setStock_record_id(stock_record_id);

        int effect = sysStockMapper.saveStockLog(sysStockLogDTO);

        if (effect == 0) {
            throw new CustomException(ErrorCode.manager_Stock.INSTOCK_FAIL);
        }
        return effect;
    }


    /**
     * 保存入库清单列表
     *
     * @param sysUser
     * @param sysStockRecordDTOS
     * @return
     */
    private String saveStockRecordList(SysUserDTO sysUser, List<SysStockRecordDTO> sysStockRecordDTOS) {

        String stock_record_id = CommonTools.createRandomString();

        //生成订单批次/
        List<SysStockRecordDTO> sysStockRecordDTOList = sysStockRecordDTOS.stream().filter(
                (string) -> string.getGoods_info_id() != null//过滤商品id为空的
        ).map((tmp) -> {
            tmp.setStock_record_id(stock_record_id);//设置批次
            tmp.setType(StockRecordType.INSTOCK.getValue());//清单类型
            tmp.setCreate_by(sysUser.getUser_id());
            tmp.setStore_id(sysUser.getStore_id());//保存店铺
            return tmp;
        }).collect(Collectors.toList());

        int effect = sysStockMapper.saveSysStockRecordDTOList(sysStockRecordDTOList);

        if (effect != sysStockRecordDTOS.size()) {
            throw new CustomException(ErrorCode.manager_Stock.INSTOCK_FAIL);
        }
        return stock_record_id;
    }

    /**
     * 通过商品ID列表查询商品信息列表
     *
     * @param sysStockRecordDTOS
     */
    private List<SysGoodsInfoDTO> getGoodsInfosById(List<SysStockRecordDTO> sysStockRecordDTOS) {
        //将商品id放入idList中
        List<SysGoodsInfoDTO> idList = sysStockRecordDTOS.stream().filter((string) -> string.getGoods_info_id() != null).map((tmp) -> {
            SysGoodsInfoDTO sysGoodsInfoDTO = new SysGoodsInfoDTO();
            sysGoodsInfoDTO.setId(tmp.getGoods_info_id());
            return sysGoodsInfoDTO;
        }).collect(Collectors.toList());

        idList = goodsInfoMapper.queryByIdList(idList);

        if (null == idList || sysStockRecordDTOS.size() > idList.size()) {
            throw new CustomException(ErrorCode.manager_Stock.INSTOCK_FAIL);
        }
        return idList;
    }
}
