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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pojo.SysGoodsInfo;
import system.controller.BaseController;
import user.dto.CtUserInfoDTO;
import user.dto.SysGoodsInfoDTO;
import user.dto.SysUserDTO;
import user.service.ICtUserInfoService;
import user.service.IGoodsInfoService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by t on 2017/8/16.
 */
@RequestMapping("GoodsInfoManagerController")
@org.springframework.stereotype.Controller
public class GoodsInfoManagerController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IGoodsInfoService goodsInfoService;

    /**
     * 进入产品入录页面
     *
     * @param model
     * @return
     */
    @MenuAuthory
    @RequestMapping("/createGoodsInfo/{menu_code}")
    public String createGoodsInfo(Model model) {
        model.addAttribute("GoodsSizeBound",GoodsSize.toList());
        return view(model, Constant.Views.createGoodsInfo);
    }


    /**
     * 进入产品列表页面
     *
     * @param model
     * @return
     */
    @MenuAuthory
    @RequestMapping("/goodsInfoList/{menu_code}")
    public String goodsInfoList(Model model) {
        model.addAttribute("GoodsSizeBound",GoodsSize.toList());
        return view(model, Constant.Views.goodsInfoList);
    }


    /**
     * 产品列表
     *
     * @param sysUser
     * @param
     * @return
     */

    @RequestMapping("/getGoodsInfoList")
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
    }



    /**
     * 删除产品
     *
     * @param sysGoodsInfoDTO
     * @return
     */
    @RequestMapping("/delGoods")
    @ResponseBody
    public ResultVo delGoods(SysGoodsInfoDTO sysGoodsInfoDTO) {

        try {
            int effect = goodsInfoService.delGoods(sysGoodsInfoDTO);
            if (effect >= 0) {
                return ResultVo.createCustomSuccess(ErrorCode.sys_error.SUCCESS_CODE, ErrorCode.sys_error.SUCCESS_MSG, null);
            }
        } catch (CustomException e) {
            log.error("产品删除失败:" + e.getMessage());
            return ResultVo.createCustomSuccess(ErrorCode.sys_error.FAIL_CODE, e.getMessage(), null);
        } catch (Exception e) {
            log.error("产品删除失败:" + e.getMessage());
        }

        return ResultVo.createCustomSuccess(ErrorCode.sys_error.FAIL_CODE, ErrorCode.sys_error.FAIL_MSG, null);
    }


    /**
     * 产品入录
     * @param sysGoodsInfoDTO
     * @param model
     * @return
     */
    @RequestMapping("/saveGoodsInfo")
    @ResponseBody
    public ResultVo saveGoodsInfo(@CurrentUser SysUserDTO sysUserDTO, SysGoodsInfoDTO sysGoodsInfoDTO,HttpServletRequest request, Model model) {

        log.info(sysGoodsInfoDTO.toString());

        try {
            sysGoodsInfoDTO.setRequestPath(request.getRealPath("/"));

            int effect = goodsInfoService.saveGoodsInfo(sysUserDTO,sysGoodsInfoDTO);

            if(effect>0){
                return ResultVo.createSuccess(ErrorCode.sys_error.SUCCESS_CODE,ErrorCode.sys_error.SUCCESS_MSG,null);
            }
        }catch (CustomException e){
            log.error("产品录入失败："+e.getMessage());
            return ResultVo.createSuccess(ErrorCode.sys_error.FAIL_CODE,e.getMessage(),null);
        }catch (Exception e) {
            e.printStackTrace();
            log.error("产品录入失败："+e.getMessage());
            return ResultVo.createSuccess(ErrorCode.sys_error.FAIL_CODE,ErrorCode.sys_error.FAIL_MSG,null);
        }
        return ResultVo.createSuccess(ErrorCode.sys_error.FAIL_CODE,ErrorCode.sys_error.FAIL_MSG,null);
    }

    /**
     * 工具类
     * 图片上传到临时目录
     *
     * @param
     * @ModelAttribute 回显的key
     */
    @RequestMapping(value = "/UploadPicTemp")
    @ResponseBody
    public ResultVo UploadPicTemp(@CurrentUser SysUserDTO sysUserDTO, @RequestParam("PicFileName") MultipartFile uploadFile, HttpServletRequest request) throws CustomException, IOException {

        String originFileName = uploadFile.getOriginalFilename();

        String real_name = System.currentTimeMillis() + "_" + originFileName;//上传到服务器的真正的图片名字

        //前半部分路径
        String leftPath = request.getRealPath("/uploadimages/"+sysUserDTO.getUser_id());


        File file = new File(leftPath, real_name);

        //判断上传文件的保存目录是否存在
        if (!file.exists() && !file.isDirectory()) {
            //创建目录
            file.mkdirs();
        }

        uploadFile.transferTo(file);

        return ResultVo.createSuccess(ErrorCode.sys_error.SUCCESS_CODE, ErrorCode.sys_error.SUCCESS_MSG,  real_name);

    }


}
