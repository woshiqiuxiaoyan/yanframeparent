package user.service.impl;

import constant.Constant;
import constant.ErrorCode;
import exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import user.dto.CtOrderDetailDTO;
import user.dto.CtOrdersDTO;
import user.dto.SysGoodsInfoDTO;
import user.dto.SysUserDTO;
import user.mapper.CtuOrderMapper;
import user.mapper.GoodsInfoMapper;
import user.mapper.SysStoreMapper;
import user.service.IConsumeService;

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


    private Logger log = LoggerFactory.getLogger(this.getClass());


    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public String accountResultActive(SysUserDTO sysUser,final CtOrdersDTO ctOrdersDTO) {

        log.info("    :"+ctOrdersDTO.toString());

        if(null==ctOrdersDTO.getCt_user_info_id()){
            throw new CustomException(ErrorCode.order_manager.NO_CHOOOSE_CUSTOM);
        }

        //生成订单详情
        List<CtOrderDetailDTO> ctOrderDetailDTOS = createOrderDetail(ctOrdersDTO);

        //算总合
        int sumMoney = getSumMoney(ctOrderDetailDTOS);

        //保存订单
        saveOrder(sysUser, ctOrdersDTO, sumMoney);

        //保存订单详情
        saveOrderDetail(ctOrdersDTO, ctOrderDetailDTOS);

        return String.valueOf(ctOrdersDTO.getOrder_id().intValue());
    }

    /**
     * 保存订单详情
     * @param ctOrdersDTO
     * @param ctOrderDetailDTOS
     */
    private void saveOrderDetail(CtOrdersDTO ctOrdersDTO, List<CtOrderDetailDTO> ctOrderDetailDTOS) {
        ctOrderDetailDTOS.stream().forEach(
                (ctOrderDetailDTOTmp )->{
                    ctOrderDetailDTOTmp.setOrder_id(ctOrdersDTO.getOrder_id());
                    ctuOrderMapper.insertOrderDetail(ctOrderDetailDTOTmp);
                }
        );
    }

    /**
     * 保存订单
     * @param sysUser
     * @param ctOrdersDTO
     * @param sumMoney
     * @return
     */
    private CtOrdersDTO saveOrder(SysUserDTO sysUser, CtOrdersDTO ctOrdersDTO, int sumMoney) {
        ctOrdersDTO.setSummoney(sumMoney);

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
     * 生成订单详情
     * @param ctOrdersDTO
     * @return
     */
    private List<CtOrderDetailDTO> createOrderDetail(CtOrdersDTO ctOrdersDTO) {
        List<CtOrderDetailDTO> ctOrderDetailDTOList =  Arrays.stream(ctOrdersDTO.getCtOrderDetailDTOS()).map((ctOrderDetailDTO)->{
            SysGoodsInfoDTO sysGoodsInfoDTO = new SysGoodsInfoDTO();
            sysGoodsInfoDTO.setId(ctOrderDetailDTO.getSys_goods_info_id());
            sysGoodsInfoDTO =  goodsInfoMapper.queryById(sysGoodsInfoDTO);
            if(sysGoodsInfoDTO==null){
                throw  new CustomException(Constant.ct_order.GOODS_NOT_EXIT);
            }
            ctOrderDetailDTO.setGoods_name(sysGoodsInfoDTO.getGoods_name());
            ctOrderDetailDTO.setGoods_price(sysGoodsInfoDTO.getGoods_sale_price());
            return   ctOrderDetailDTO;
        }).collect(Collectors.toList());

        ctOrderDetailDTOList.stream().forEach(System.out::println);

        return ctOrderDetailDTOList;
    }
}
