package user.controller;

import annotation.CurrentUser;
import annotation.MenuAuthory;
import com.github.pagehelper.Page;
import constant.Constant;
import constant.ErrorCode;
import dto.ResultVo;
import dto.ResultVoPage;
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
import user.dto.SysStockDTO;
import user.dto.SysStockRecordDTO;
import user.dto.SysUserDTO;
import user.service.ICtUserInfoService;
import user.service.ISysStockService;

import java.util.ArrayList;

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
     * 库存列表页面
     *
     * @param model
     * @return
     */
    @MenuAuthory
    @RequestMapping("/stockListPage/{menu_code}")
    public String stockListPage(Model model) {
        model.addAttribute("GoodsSizeBound", GoodsSize.toList());
        return view(model, Constant.Views.stockListPage);
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




    /**
     * 库存列表
     * @param
     * @param
     * @return
     */
    @RequestMapping("/getStockInfoList")
    @ResponseBody
    public ResultVoPage getStockInfoList(@CurrentUser SysUserDTO sysUser, SysStockDTO sysStockDTO ) {

        try {

            Page<SysStockDTO> sysStockDTOPage = sysStockService.getStockInfoList(sysUser, sysStockDTO);

            if (null != sysStockDTOPage && sysStockDTOPage.size() > 0) {
                return ResultVoPage.createCustomSuccess(0, ErrorCode.sys_error.SUCCESS_MSG, sysStockDTOPage, sysStockDTOPage.getTotal());
            }
        } catch (CustomException e) {
            log.error("库存列表查询:" + e.getMessage());
            return ResultVoPage.createCustomSuccess(1, e.getMessage(), new ArrayList<>(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("库存列表查询失败:" + e.getMessage());
        }
        return ResultVoPage.createCustomSuccess(1, ErrorCode.sys_error.SUCCESS_MSG, new ArrayList<>(), 0);
    }



}
