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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pojo.CtOrders;
import system.controller.BaseController;
import user.dto.CtOrdersDTO;
import user.dto.SysGoodsInfoDTO;
import user.dto.SysUserDTO;
import user.service.IConsumeService;
import user.service.IGoodsInfoService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
        return view(model, Constant.Views.consumePage);
    }



    /**
     * 结算
     * @param sysUser
     * @param
     * @return
     */

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



}
