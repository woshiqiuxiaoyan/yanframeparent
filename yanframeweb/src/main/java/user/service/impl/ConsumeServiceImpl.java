package user.service.impl;

import constant.Constant;
import constant.ErrorCode;
import exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.dto.CtOrdersDTO;
import user.dto.SysGoodsInfoDTO;
import user.dto.SysUserDTO;
import user.mapper.CtuOrderMapper;
import user.mapper.GoodsInfoMapper;
import user.mapper.SysStoreMapper;
import user.service.IConsumeService;

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


    @Override
    public String accountResultActive(SysUserDTO sysUser, CtOrdersDTO ctOrdersDTO) {

        log.info("    :"+ctOrdersDTO.toString());

        if(null==ctOrdersDTO.getCt_user_info_id()){
            throw new CustomException(ErrorCode.sys_error.PARAM_FAIL);
        }

        int sumMoney =  Stream.of(ctOrdersDTO.getCtOrderDetailDTOS()).map((ctOrderDetailDTO)->{
            SysGoodsInfoDTO sysGoodsInfoDTO = new SysGoodsInfoDTO();
            sysGoodsInfoDTO.setId(ctOrderDetailDTO.getSys_goods_info_id());
            sysGoodsInfoDTO =  goodsInfoMapper.queryById(sysGoodsInfoDTO);
            if(sysGoodsInfoDTO==null){
                throw  new CustomException(Constant.ct_order.GOODS_NOT_EXIT);
            }
            return   ctOrderDetailDTO.getGoods_num()*sysGoodsInfoDTO.getGoods_sale_price();
        }).collect(Collectors.summingInt((money)->money));


        ctOrdersDTO.setSummoney(sumMoney);

        ctOrdersDTO.setCreate_by(sysUser.getUser_id());

        int effect = ctuOrderMapper.insertCtOrder(ctOrdersDTO);

        if(effect==0){
            throw new CustomException(Constant.ct_order.INSERT_ORDER_FAIL);
        }



        return null;
    }
}
