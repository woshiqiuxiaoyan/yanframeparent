package user.controller;

import annotation.CurrentUser;
import annotation.MenuAuthory;
import com.github.pagehelper.Page;
import constant.Constant;
import constant.ErrorCode;
import dto.ResultVo;
import dto.ResultVoPage;
import exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import system.controller.BaseController;
import user.dto.CtUserGradeDTO;
import user.dto.CtUserInfoDTO;
import user.dto.SysUserDTO;
import user.service.ICtUserInfoService;

import java.util.ArrayList;

/**
 * Created by t on 2017/8/16.
 */
@RequestMapping("CtuManagerController")
@org.springframework.stereotype.Controller
public class CtuManagerController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    public ICtUserInfoService ctUserInfoService;



    /**
     * 会员开卡页面
     *
     * @param menu_code
     * @param model
     * @return
     */
    @MenuAuthory
    @RequestMapping("/createCard/{menu_code}")
    public String createCard(@PathVariable String menu_code, Model model) {

        return view(model, Constant.Views.createCard);
    }


    /**
     * 会员等级页面
     * @param model
     * @return
     */
    @MenuAuthory
    @RequestMapping("/ctUserGrade/{menu_code}")
    public String ctUserGrade( Model model) {
        return view(model, Constant.Views.ctUserGrade);
    }



    /**
     * 会员等级列表
     * @param ctUserGradeDTO
     * @return
     */
    @RequestMapping("/getCtUserGradeList")
    @ResponseBody
    public ResultVoPage getCtUserGradeList(CtUserGradeDTO ctUserGradeDTO) {
        try {
            Page<CtUserGradeDTO> ctUserInfoDTOList = ctUserInfoService.getCtUserGradeList(ctUserGradeDTO);

            if (null != ctUserInfoDTOList && ctUserInfoDTOList.size() > 0) {
                return ResultVoPage.createCustomSuccess(0, ErrorCode.sys_error.SUCCESS_MSG, ctUserInfoDTOList, ctUserInfoDTOList.getTotal());
            }
        } catch (CustomException e) {
            log.error("会员等级列表查询:" + e.getMessage());
            return ResultVoPage.createCustomSuccess(1, ErrorCode.sys_error.SUCCESS_MSG, new ArrayList<>(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("会员等级列表查询失败:" + e.getMessage());
        }


        return ResultVoPage.createCustomSuccess(1, ErrorCode.sys_error.SUCCESS_MSG, new ArrayList<>(), 0);
    }


    /**
     * 增加会员等级
     * @param sysUserDTO
     * @param ctUserGradeDTO
     * @return
     */
    @RequestMapping("/addCtUserGrade")
    @ResponseBody
    public ResultVo addCtUserGrade(@CurrentUser SysUserDTO sysUserDTO, CtUserGradeDTO ctUserGradeDTO){
        try {
            int effect =  ctUserInfoService.addCtUserGrade(ctUserGradeDTO);
            if(effect>0){
              return   ResultVo.create(ErrorCode.sys_error.SUCCESS_CODE,ErrorCode.sys_error.SUCCESS_MSG);
            }
        }catch (CustomException e){
            log.error("增加会员等级失败："+e.getMessage());
            return  ResultVo.create(ErrorCode.sys_error.FAIL_CODE,e.getMessage());
        }
        catch (Exception e) {
            log.error("增加会员等级失败："+e.getMessage());
        }
        return  ResultVo.create(ErrorCode.sys_error.FAIL_CODE,ErrorCode.sys_error.FAIL_MSG);
    }






    /**
     * 修改会员等级
     * @param sysUser
     * @param ctUserGradeDTO
     * @return
     */
    @RequestMapping("/updateCtUserGrade")
    @ResponseBody
    public ResultVo updateCtUserGrade(@CurrentUser SysUserDTO sysUser, CtUserGradeDTO ctUserGradeDTO) {
        try {
            int effect = ctUserInfoService.updateCtUserGrade(sysUser, ctUserGradeDTO);
            if (effect >= 0) {
                return ResultVo.createCustomSuccess(ErrorCode.sys_error.SUCCESS_CODE, ErrorCode.sys_error.SUCCESS_MSG, null);
            }
        } catch (CustomException e) {
            log.error("会员等级更新失败:" + e.getMessage());
            return ResultVo.createCustomSuccess(ErrorCode.sys_error.FAIL_CODE, e.getMessage(), null);
        } catch (Exception e) {
            log.error("会员等级更新失败:" + e.getMessage());
        }

        return ResultVo.createCustomSuccess(ErrorCode.sys_error.FAIL_CODE, ErrorCode.sys_error.FAIL_MSG, null);
    }

    /**
     * 删除会员等级
     * @param id
     * @return
     */
    @RequestMapping("/delGrade")
    @ResponseBody
    public ResultVo delGrade(  @RequestParam("id") Integer id){
        try {
            int effect = ctUserInfoService.delCtUserGrade(id);
            if (effect >= 0) {
                return ResultVo.createCustomSuccess(ErrorCode.sys_error.SUCCESS_CODE, ErrorCode.sys_error.SUCCESS_MSG, null);
            }
        } catch (CustomException e) {
            log.error("删除会员等级更新失败:" + e.getMessage());
            return ResultVo.createCustomSuccess(ErrorCode.sys_error.FAIL_CODE, e.getMessage(), null);
        } catch (Exception e) {
            log.error("删除会员等级更新失败:" + e.getMessage());
        }

        return ResultVo.createCustomSuccess(ErrorCode.sys_error.FAIL_CODE, ErrorCode.sys_error.FAIL_MSG, null);
    }


    /**
     * 新增会员
     *
     * @param ctUserInfoDTO
     * @return
     */
    @RequestMapping("/createCtUserInfo")
    @ResponseBody
    public ResultVo createCtUserInfo(@CurrentUser SysUserDTO sysUser, CtUserInfoDTO ctUserInfoDTO) {


        try {
            int effect = ctUserInfoService.creatCard(sysUser, ctUserInfoDTO);
            if (effect >= 0) {
                return ResultVo.createCustomSuccess(ErrorCode.sys_error.SUCCESS_CODE, ErrorCode.sys_error.SUCCESS_MSG, null);
            }
        } catch (CustomException e) {
            log.error("会员开卡失败:" + e.getMessage());
            return ResultVo.createCustomSuccess(ErrorCode.sys_error.FAIL_CODE, e.getMessage(), null);
        } catch (Exception e) {
            log.error("会员开卡失败:" + e.getMessage());
        }

        return ResultVo.createCustomSuccess(ErrorCode.sys_error.FAIL_CODE, ErrorCode.sys_error.FAIL_MSG, null);
    }







    /**
     * 修改会员
     *
     * @param ctUserInfoDTO
     * @return
     */
    @RequestMapping("/updateCtUserInfo")
    @ResponseBody
    public ResultVo updateCtUserInfo(@CurrentUser SysUserDTO sysUser, CtUserInfoDTO ctUserInfoDTO) {


        try {
            int effect = ctUserInfoService.updateCtUserInfo(sysUser, ctUserInfoDTO);
            if (effect >= 0) {
                return ResultVo.createCustomSuccess(ErrorCode.sys_error.SUCCESS_CODE, ErrorCode.sys_error.SUCCESS_MSG, null);
            }
        } catch (CustomException e) {
            log.error("会员更新失败:" + e.getMessage());
            return ResultVo.createCustomSuccess(ErrorCode.sys_error.FAIL_CODE, e.getMessage(), null);
        } catch (Exception e) {
            log.error("会员更新失败:" + e.getMessage());
        }

        return ResultVo.createCustomSuccess(ErrorCode.sys_error.FAIL_CODE, ErrorCode.sys_error.FAIL_MSG, null);
    }


    /**
     * 删除会员
     * @param ctUserInfoDTO
     * @return
     */
    @RequestMapping("/delCtuser")
    @ResponseBody
    public ResultVo delCtuser(CtUserInfoDTO ctUserInfoDTO) {

        try {
            int effect = ctUserInfoService.delCtuser(ctUserInfoDTO);
            if (effect >= 0) {
                return ResultVo.createCustomSuccess(ErrorCode.sys_error.SUCCESS_CODE, ErrorCode.sys_error.SUCCESS_MSG, null);
            }
        } catch (CustomException e) {
            log.error("会员删除失败:" + e.getMessage());
            return ResultVo.createCustomSuccess(ErrorCode.sys_error.FAIL_CODE, e.getMessage(), null);
        } catch (Exception e) {
            log.error("会员删除失败:" + e.getMessage());
        }

        return ResultVo.createCustomSuccess(ErrorCode.sys_error.FAIL_CODE, ErrorCode.sys_error.FAIL_MSG, null);
    }



    /**
     * 会员列表
     *
     * @return
     */
    @RequestMapping(value = "/ctuserList/{menu_code}", method = RequestMethod.GET)
    public String ctuserList(Model model) {
        log.info("会员列表。。。。");
        return view(model, Constant.Views.customList);

    }


    /**
     * 会员列表
     *
     * @param sysUser
     * @param
     * @return
     */

    @RequestMapping("/getCtuserList")
    @ResponseBody
    public ResultVoPage getCtuserList(@CurrentUser SysUserDTO sysUser, CtUserInfoDTO ctUserInfoDTO) {


        try {
            Page<CtUserInfoDTO> ctUserInfoDTOList = ctUserInfoService.ctuserList(sysUser, ctUserInfoDTO);

            if (null != ctUserInfoDTOList && ctUserInfoDTOList.size() > 0) {
                return ResultVoPage.createCustomSuccess(0, ErrorCode.sys_error.SUCCESS_MSG, ctUserInfoDTOList, ctUserInfoDTOList.getTotal());
            }
        } catch (CustomException e) {
            log.error("会员列表查询:" + e.getMessage());
            return ResultVoPage.createCustomSuccess(1, ErrorCode.sys_error.SUCCESS_MSG, new ArrayList<>(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("会员列表查询失败:" + e.getMessage());
        }
        return ResultVoPage.createCustomSuccess(1, ErrorCode.sys_error.SUCCESS_MSG, new ArrayList<>(), 0);
    }


}
