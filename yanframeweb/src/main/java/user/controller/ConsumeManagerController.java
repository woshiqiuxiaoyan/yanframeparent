package user.controller;

import annotation.CurrentUser;
import annotation.MenuAuthory;
import com.github.pagehelper.Page;
import constant.Constant;
import constant.ErrorCode;
import dto.ResultVo;
import dto.ResultVoPage;
import exception.CustomException;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import system.controller.BaseController;
import user.dto.CtOrdersDTO;
import user.dto.SysUserDTO;
import user.service.IConsumeService;

import java.util.List;

/**
 * Created by t on 2017/8/16.
 */
@RequestMapping("ConsumeManagerController")
@Controller
public class ConsumeManagerController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IConsumeService consumeService;


    /**
     * 进入快速消费页面
     * @param model
     * @return
     */
    @MenuAuthory
    @RequestMapping("/consumePage/{menu_code}")
    public String consumePage(Model model) {
        log.info("-------------------------进入快速消费页面----------------------------");
        model.addAttribute("title","快速消费");
        return view(model, Constant.Views.consumePage);
    }

    /**
     * 打印小票页面
     * @param model
     * @return
     */
    @MenuAuthory
    @RequestMapping("/printBill/{menu_code}")
    public String printBill(@CurrentUser SysUserDTO sysUserDTO, @RequestParam("orderId") String orderId, Model model) {
//        CtOrdersDTO ctOrdersDTO =  consumeService.getBillData(sysUserDTO,orderId);

        model.addAttribute("title",sysUserDTO.getStore_id());
        return "admin/OrderManager/quickconsumebill";
    }



    /**
     * 结算
     * @param sysUser
     * @param
     * @return
     */
    @ApiOperation(nickname = "accountResultActive", value = "结算", notes = "结算")
    @RequestMapping(value = "/accountResultActive", consumes = "application/json; charset=UTF-8")
    @ResponseBody
    public ResultVo accountResultActive(@CurrentUser SysUserDTO sysUser,@RequestBody  CtOrdersDTO  ctOrdersDTO ) {

        try{

            String orderId =  consumeService.accountResultActive(sysUser,ctOrdersDTO);

            if(StringUtils.isNotBlank(orderId)){
                return ResultVo.createCustomSuccess( ErrorCode.sys_error.SUCCESS_CODE, ErrorCode.sys_error.SUCCESS_MSG,orderId);
            }

        } catch (CustomException e) {
            log.error("结算失败:" + e.getMessage());
            return ResultVo.createCustomSuccess(ErrorCode.sys_error.FAIL_CODE, e.getMessage(),null);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("结算失败:" + e.getMessage());
        }
        return ResultVo.createCustomSuccess( ErrorCode.sys_error.FAIL_CODE, ErrorCode.sys_error.FAIL_MSG,null);
    }

    /**
     * 进入订单列表页面
     * @param model
     * @return
     */
    @MenuAuthory
    @RequestMapping("/orderList/{menu_code}")
    public String orderList(Model model) {
        log.info("-------------------------进入订单列表页面----------------------------");
        model.addAttribute("title","订单列表");
        return view(model, Constant.Views.orderList);
    }

    /**
     * 订单列表
     * @param sysUser
     * @param
     * @return
     */
    @ApiOperation(nickname = "getOrdersList", value = "订单列表", notes = "订单列表")
    @RequestMapping(value = "/getOrdersList")
    @ResponseBody
    public ResultVoPage getOrdersList(@CurrentUser SysUserDTO sysUser,CtOrdersDTO  ctOrdersDTO ) {

        try{

            Page<CtOrdersDTO> ctOrdersDTOList =  consumeService.getOrdersList(sysUser,ctOrdersDTO);

            if(null!=ctOrdersDTOList  ){
                return ResultVoPage.createCustomSuccess(0, ErrorCode.sys_error.SUCCESS_MSG, ctOrdersDTOList, ctOrdersDTOList.getTotal());
            }

        } catch (CustomException e) {
            log.error("订单列表:" + e.getMessage());
            return ResultVoPage.createSuccess(ErrorCode.sys_error.FAIL_CODE,e.getMessage(),null);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("订单列表:" + e.getMessage());
        }
        return ResultVoPage.createSuccess(ErrorCode.sys_error.FAIL_CODE, ErrorCode.sys_error.FAIL_MSG);
    }




    /**
     * 订单详情
     * @param sysUser
     * @param
     * @return
     */
    @ApiOperation(nickname = "getOrdersDetail", value = "订单详情", notes = "订单详情")
    @RequestMapping(value = "/getOrdersDetail")
    @ResponseBody
    public ResultVo getOrdersDetail(@CurrentUser SysUserDTO sysUser,CtOrdersDTO  ctOrdersDTO ) {

        try{

            ctOrdersDTO =  consumeService.getOrdersDetail(sysUser,ctOrdersDTO);

            if(null!=ctOrdersDTO  ){
                return ResultVo.createSuccess(ErrorCode.sys_error.SUCCESS_CODE,ErrorCode.sys_error.SUCCESS_MSG,ctOrdersDTO);
            }
        } catch (CustomException e) {
            log.error("查询订单详情失败:" + e.getMessage());
            return ResultVo.create(ErrorCode.sys_error.FAIL_CODE,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询订单详情失败:" + e.getMessage());
        }
        return ResultVo.create(ErrorCode.sys_error.FAIL_CODE, ErrorCode.sys_error.FAIL_MSG);
    }

}
