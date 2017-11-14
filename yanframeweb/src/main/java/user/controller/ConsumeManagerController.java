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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import system.controller.BaseController;
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
     *
     * @param sysUser
     * @param
     * @return
     */

  /*  @RequestMapping("/getGoodsInfoList")
    @ResponseBody
    public ResultVoPage getGoodsInfoList(@CurrentUser SysUserDTO sysUser, SysGoodsInfoDTO sysGoodsInfoDTO,HttpServletRequest request) {


        try {
            Page<SysGoodsInfoDTO> sysGoodsInfoDTOPage = goodsInfoService.getGoodsInfoList(sysUser, sysGoodsInfoDTO);

            if (null != sysGoodsInfoDTOPage && sysGoodsInfoDTOPage.size() > 0) {
                return ResultVoPage.createCustomSuccess(0, ErrorCode.sys_error.SUCCESS_MSG, sysGoodsInfoDTOPage, sysGoodsInfoDTOPage.getTotal());
            }
        } catch (CustomException e) {
            log.error("产品列表查询:" + e.getMessage());
            return ResultVoPage.createCustomSuccess(1, e.getMessage(), new ArrayList<>(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("产品列表查询失败:" + e.getMessage());
        }
        return ResultVoPage.createCustomSuccess(1, ErrorCode.sys_error.SUCCESS_MSG, new ArrayList<>(), 0);
    }*/



}
