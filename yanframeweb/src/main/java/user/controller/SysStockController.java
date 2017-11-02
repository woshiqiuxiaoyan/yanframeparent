package user.controller;

import annotation.CurrentUser;
import annotation.MenuAuthory;
import constant.Constant;
import constant.ErrorCode;
import dto.ResultVo;
import enums.GoodsSize;
import exception.CustomException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.controller.BaseController;
import user.dto.SysStockRecordDTO;
import user.dto.SysUserDTO;
import user.service.ICtUserInfoService;
import user.service.ISysStockService;

/**
 * Created by t on 2017/8/16.
 *
 * @Author qxy
 */
@RequestMapping("SysStockController")
@org.springframework.stereotype.Controller
public class SysStockController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private ISysStockService sysStockService;


    /**
     * 进货页面
     *
     * @param model
     * @return
     */
    @MenuAuthory
    @RequestMapping("/inStockPage/{menu_code}")
    public String inStockPage(Model model) {
        model.addAttribute("GoodsSizeBound", GoodsSize.toList());
        return view(model, Constant.Views.inStockPage);
    }


    /**
     * 进货 (相关表  sys_stock/sys_stock_record/sys_stock_log)
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/inStock", consumes = "application/json; charset=UTF-8")
    @ResponseBody
    public ResultVo inStock(@CurrentUser SysUserDTO sysUserDTO, @RequestBody SysStockRecordDTO[] sysStockRecordDTOS, Model model) {
        log.info(sysStockRecordDTOS.toString());

        try {
            String stock_record_id = sysStockService.inStock(sysUserDTO, sysStockRecordDTOS);

            if (StringUtils.isNotBlank(stock_record_id )){
                return ResultVo.createSuccess(ErrorCode.sys_error.SUCCESS_CODE, ErrorCode.sys_error.SUCCESS_MSG, stock_record_id);
            }
        } catch (CustomException e) {
            e.printStackTrace();
            log.error("进货失败：" + e.getMessage());
            return ResultVo.createSuccess(ErrorCode.sys_error.FAIL_CODE, e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("进货失败：" + e.getMessage());
        }
        return ResultVo.createSuccess(ErrorCode.sys_error.FAIL_CODE, ErrorCode.sys_error.FAIL_MSG, null);
    }


}
