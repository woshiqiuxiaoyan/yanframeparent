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
import pojo.CtOrderDetail;
import user.dto.*;
import user.mapper.*;
import user.service.IConsumeService;
import utils.CommonTools;

import javax.persistence.RollbackException;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>Title:AccountService </p>
 * <p>Description:</p>
 * Created with IntelliJ IDEA.
 * User: qxy
 * Date: 2017/10/9
 * Time: 9:54
 */
@Service
public class ConsumeServiceImpl implements IConsumeService {

    @Autowired
    private CtuOrderMapper ctuOrderMapper;

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Autowired
    private CtUserInfoMapper ctUserInfoMapper;

    @Autowired
    private SysStockMapper sysStockMapper;


    private Logger log = LoggerFactory.getLogger(this.getClass());


    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public String accountResultActive(SysUserDTO sysUser,final CtOrdersDTO ctOrdersDTO) {

        log.info("结算:"+ctOrdersDTO.toString());

        if(sysUser.getUser_id()==null){
            //登录异常
            throw new CustomException(ErrorCode.sys_user.NO_LOGIN_ERROR);
        }

        if(null==ctOrdersDTO.getCt_user_info_id()){
            throw new CustomException(ErrorCode.order_manager.NO_CHOOOSE_CUSTOM);
        }
        //设置店铺
        ctOrdersDTO.setStore_id(sysUser.getStore_id());

        log.info("------------生成订单详情DTO 检查商品和库存------------");
        List<CtOrderDetailDTO> ctOrderDetailDTOS = createOrderDetail(ctOrdersDTO);

        //算总合
        int sumMoney = getSumMoney(ctOrderDetailDTOS);

        ctOrdersDTO.setSummoney(sumMoney);

        //保存订单
        saveOrder(sysUser, ctOrdersDTO);

        //保存订单详情
        saveOrderDetail(ctOrdersDTO.getOrder_id(), ctOrderDetailDTOS);

        //修改会员积分
        updateUserIntegral(ctOrdersDTO);

        //保存库存清单
        String stock_record_id =  saveStockRecordList(sysUser, ctOrderDetailDTOS);

        //更新库存数量 增加履历
        updateStockNum(sysUser, ctOrderDetailDTOS,stock_record_id);

        return String.valueOf(ctOrdersDTO.getOrder_id().intValue());
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
    private int saveSysStockLog(Integer stock_id, String remark, Integer user_id, Integer num, Integer goods_instock_price, String stock_record_id) {

        SysStockLogDTO sysStockLogDTO = new SysStockLogDTO();

        sysStockLogDTO.setStock_id(stock_id);

        sysStockLogDTO.setType(StockType.OUTSTOCK.getValue());

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
     * 更新库存数量
     * @param sysUser
     * @param ctOrderDetailDTOS
     */
    private void updateStockNum(SysUserDTO sysUser, List<CtOrderDetailDTO> ctOrderDetailDTOS,String stock_record_id) {
        ctOrderDetailDTOS.stream().forEach(
            ctOrderDetailDTO -> {
                SysStockDTO sysStockDTO = new SysStockDTO();
                sysStockDTO.setStore_id(sysUser.getStore_id());//设置店铺
                sysStockDTO.setGoods_info_id(ctOrderDetailDTO.getSys_goods_info_id());//设置商品
                sysStockDTO.setNum(ctOrderDetailDTO.getGoods_num()*-1);//设置操作数量
                sysStockDTO.setRemark("快速消费");
                int effect =  sysStockMapper.updateStockNum(sysStockDTO);
                if(effect==0){
                    log.info("更新库存失败");
                    throw new CustomException(Constant.ct_order.INSERT_ORDER_FAIL);
                }
                log.info("----保存库存履历----");
                saveSysStockLog(sysStockDTO.getId(),"快速消费",sysUser.getUser_id()
                        ,ctOrderDetailDTO.getGoods_num(),ctOrderDetailDTO.getGoods_price(),stock_record_id);

            }
        );
    }


    /**
     * 保存入库清单列表
     * @param sysUser
     * @param ctOrderDetailDTOList
     * @return
     */
    private String saveStockRecordList(SysUserDTO sysUser, List<CtOrderDetailDTO> ctOrderDetailDTOList) {

        //生成批次单号
        String stock_record_id = CommonTools.createRandomString();

        //生成订单批次/
        List<SysStockRecordDTO> sysStockRecordDTOList = ctOrderDetailDTOList.stream().map(
                ctOrderDetailDTO -> {
                    SysStockRecordDTO tmp = new SysStockRecordDTO();
                    tmp.setStock_record_id(stock_record_id);
                    tmp.setNum(ctOrderDetailDTO.getGoods_num());
                    tmp.setGoods_info_id(ctOrderDetailDTO.getSys_goods_info_id());
                    tmp.setType(StockRecordType.OUTSTOCK.getValue());
                    tmp.setRemark(ctOrderDetailDTO.getRemark());
                    tmp.setCreate_by(sysUser.getUser_id());
                    tmp.setStore_id(sysUser.getStore_id());
                    return tmp;
                }
        ).collect(Collectors.toList());


        int effect = sysStockMapper.saveSysStockRecordDTOList(sysStockRecordDTOList);

        if (effect != ctOrderDetailDTOList.size()) {
            throw new CustomException(ErrorCode.manager_Stock.INSTOCK_FAIL);
        }

        return stock_record_id;
    }








    /**
     * 修改会员积分
     * @param ctOrdersDTO
     */
    private void updateUserIntegral(CtOrdersDTO ctOrdersDTO) {

        int effect = ctUserInfoMapper.updateCtUserIntegral(ctOrdersDTO);

        if(effect==0){
            throw new CustomException(Constant.ct_order.INSERT_ORDER_FAIL);
        }
    }

    /**
     * 获取订单列表
     * @param sysUser
     * @param ctOrdersDTO
     * @return
     */
    @Override
    public Page<CtOrdersDTO> getOrdersList(SysUserDTO sysUser, CtOrdersDTO ctOrdersDTO) {

        ctOrdersDTO.setStore_id(sysUser.getStore_id());

        Page<CtOrdersDTO> list = PageHelper.startPage(ctOrdersDTO.getPage(), ctOrdersDTO.getLimit())
                .doSelectPage(() ->ctuOrderMapper.queryOrder(ctOrdersDTO));

        return list;
    }

    /**
     * 订单详情
     * @param sysUser
     * @param ctOrdersDTO
     * @return
     */
    @Override
    public CtOrdersDTO getOrdersDetail(SysUserDTO sysUser, CtOrdersDTO ctOrdersDTO) {

        List<CtOrdersDTO> ctOrdersDTOList =  ctuOrderMapper.queryOrder(ctOrdersDTO);

        if(ctOrdersDTOList!=null && ctOrdersDTOList.size()>0){
            ctOrdersDTO =  ctOrdersDTOList.get(0);
            List<CtOrderDetailDTO> ctOrderDetailDTOList =  ctuOrderMapper.queryOrderDetail(ctOrdersDTO);
            if(ctOrderDetailDTOList!=null && ctOrderDetailDTOList.size()>0){

                ctOrderDetailDTOList.stream().map(ctOrderDetailDTO -> {
                    String[] img_url_show = GoodsInfoServiceImpl.setImgurl(ctOrderDetailDTO.getGoods_img_url());
                    ctOrderDetailDTO.setImg_url_show(img_url_show);
                    return ctOrderDetailDTO;
                }).collect(Collectors.toList());

                CtOrderDetailDTO [] ctOrderDetailDTOS = new CtOrderDetailDTO[ctOrderDetailDTOList.size()];
                ctOrdersDTO.setCtOrderDetailDTOS( ctOrderDetailDTOList.toArray(ctOrderDetailDTOS));
                return ctOrdersDTO;
            }
        }

        return null;
    }




    /**
     * 保存订单详情
     * @param ctOrderDetailDTOS
     */
    private void saveOrderDetail(Integer order_id, List<CtOrderDetailDTO> ctOrderDetailDTOS) {
        ctOrderDetailDTOS.stream().forEach(
                (ctOrderDetailDTOTmp )->{
                    ctOrderDetailDTOTmp.setOrder_id(order_id);
                    ctuOrderMapper.insertOrderDetail(ctOrderDetailDTOTmp);
                }
        );
    }

    /**
     * 保存订单
     * @param sysUser
     * @param ctOrdersDTO
     * @return
     */
    private CtOrdersDTO saveOrder(SysUserDTO sysUser, CtOrdersDTO ctOrdersDTO ) {

        ctOrdersDTO.setStore_id(sysUser.getStore_id());

        ctOrdersDTO.setCreate_by(sysUser.getUser_id());

        int effect = ctuOrderMapper.insertCtOrder(ctOrdersDTO);

        if(effect==0){
            throw new CustomException(Constant.ct_order.INSERT_ORDER_FAIL);
        }
        return ctOrdersDTO;
    }

    /**
     * 算总合
     * @param ctOrderDetailDTOS
     * @return
     */
    private int getSumMoney(List<CtOrderDetailDTO> ctOrderDetailDTOS) {
        return ctOrderDetailDTOS.parallelStream().map(
                (ctOrderDetailDTO)->{
                    return  ctOrderDetailDTO.getGoods_num()*ctOrderDetailDTO.getGoods_price();
                }
            ).collect(Collectors.summingInt((money)->money));
    }

    /**
     * 生成订单详情DTO 检查商品和库存
     * @param ctOrdersDTO
     * @return
     */
    private List<CtOrderDetailDTO> createOrderDetail(CtOrdersDTO ctOrdersDTO) {

        List<CtOrderDetailDTO> ctOrderDetailDTOList =  Arrays.stream(ctOrdersDTO.getCtOrderDetailDTOS()).filter(
                (string)->string.getStock_id()!=null //过滤库存ID不为空
        ).map(
            ctOrderDetailDTO->{
                SysStockDTO sysStockDTO = new SysStockDTO();
                sysStockDTO.setId(ctOrderDetailDTO.getStock_id());
                sysStockDTO.setStore_id(ctOrdersDTO.getStore_id());
                //查库存
                List<SysStockDTO> sysStockDTOList =   sysStockMapper.queryByCondition(sysStockDTO);
                if(sysStockDTOList!=null && sysStockDTOList.size()==1
                        &&ctOrderDetailDTO.getGoods_num()<=sysStockDTOList.get(0).getTotal()){
                    ctOrderDetailDTO.setGoods_name(sysStockDTOList.get(0).getGoods_name());
                    ctOrderDetailDTO.setGoods_price(sysStockDTOList.get(0).getGoods_sale_price());
                    ctOrderDetailDTO.setRemark(ctOrdersDTO.getRemark());
                    ctOrderDetailDTO.setSys_goods_info_id(sysStockDTOList.get(0).getGoods_info_id());
                    ctOrderDetailDTO.setStock_id(sysStockDTOList.get(0).getId());
                    return   ctOrderDetailDTO;
                }
                throw  new CustomException(Constant.ct_order.GOODS_NOT_EXIT);
        }).collect(Collectors.toList());
        return ctOrderDetailDTOList;
    }
}
