package user.controller;

import annotation.CurrentUser;
import annotation.MenuAuthory;
import com.github.pagehelper.Page;
import constant.Constant;
import constant.ErrorCode;
import dto.ResultVoPage;
import exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.SysStore;
import system.controller.BaseController;
import user.dto.SysRoleDTO;
import user.dto.SysStoreDTO;
import user.dto.SysUserDTO;
import user.service.IAccountService;

import java.util.ArrayList;

/**
 * <p>Title:SysUserInfoManager </p>
 * <p>Description:系统用户管理</p>
 * Created with IntelliJ IDEA.
 * User: qxy
 * Date: 2017/11/6
 * Time: 10:02
 */

@RequestMapping("SysUserInfoManager")
@Controller
public class SysUserInfoManager extends BaseController {


    private Logger log = LoggerFactory.getLogger(SysUserInfoManager.class);

    @Autowired
    private IAccountService accountService;

    /**
     * 系统用户页面
     *
     * @param model
     * @return
     */
    @MenuAuthory
    @RequestMapping("/sysUserInfoPage/{menu_code}")
    public String sysUserInfoPage(Model model) {
        return view(model, Constant.Views.sysUserInfoList);
    }



    /**
     * 系统用户列表
     * @param sysUser
     * @param
     * @return
     */

    @RequestMapping("/getSysUserInfoList")
    @ResponseBody
    public ResultVoPage getSysUserInfoList(@CurrentUser SysUserDTO sysUser, SysUserDTO sysUserDTO) {


        try {
            Page<SysUserDTO> sysUserInfoList = accountService.getSysUserInfoList(sysUser, sysUserDTO);

            if (null != sysUserInfoList && sysUserInfoList.size() > 0) {
                return ResultVoPage.createCustomSuccess(0, ErrorCode.sys_error.SUCCESS_MSG, sysUserInfoList, sysUserInfoList.getTotal());
            }
        } catch (CustomException e) {
            log.error("系统用户列表查询:" + e.getMessage());
            return ResultVoPage.createCustomSuccess(1, ErrorCode.sys_error.SUCCESS_MSG, new ArrayList<>(), 0);
        } catch (Exception e) {
            log.error("系统用户列表查询失败:" + e.getMessage());
        }
        return ResultVoPage.createCustomSuccess(1, ErrorCode.sys_error.SUCCESS_MSG, new ArrayList<>(), 0);
    }



    /**
     * 系统角色列表
     * @param sysRoleDTO
     * @return
     */
    @RequestMapping("/getSysRoleList")
    @ResponseBody
    public ResultVoPage getSysRoleList(@CurrentUser SysUserDTO sysUser, SysRoleDTO sysRoleDTO) {


        try {

            Page<SysRoleDTO> sysRoleDTOPage = accountService.getSysRoleList(sysUser, sysRoleDTO);

            if (null != sysRoleDTOPage && sysRoleDTOPage.size() > 0) {
                return ResultVoPage.createCustomSuccess(0, ErrorCode.sys_error.SUCCESS_MSG, sysRoleDTOPage, sysRoleDTOPage.getTotal());
            }
        } catch (CustomException e) {
            log.error("系统角色列表查询:" + e.getMessage());
            return ResultVoPage.createCustomSuccess(1, ErrorCode.sys_error.SUCCESS_MSG, new ArrayList<>(), 0);
        } catch (Exception e) {
            log.error("系统角色列表查询失败:" + e.getMessage());
        }
        return ResultVoPage.createCustomSuccess(1, ErrorCode.sys_error.SUCCESS_MSG, new ArrayList<>(), 0);
    }



    /**
     * 店铺列表(管理员才能看)
     * @param sysStoreDTO
     * @return
     */
    @RequestMapping("/getSysStoreList")
    @ResponseBody
    public ResultVoPage getSysStoreList(@CurrentUser SysUserDTO sysUser, SysStoreDTO sysStoreDTO) {


        try {

            if(sysUser.getIs_shop_keeper().intValue()!=1){
                throw new CustomException(ErrorCode.sys_error.NOT_PERMISSION);
            }
            Page<SysStoreDTO> sysStoreDTOPage = accountService.getSysStoreList(sysUser, sysStoreDTO);

            if (null != sysStoreDTOPage && sysStoreDTOPage.size() > 0) {
                return ResultVoPage.createCustomSuccess(0, ErrorCode.sys_error.SUCCESS_MSG, sysStoreDTOPage, sysStoreDTOPage.getTotal());
            }
        } catch (CustomException e) {
            log.error("店铺列表查询:" + e.getMessage());
            return ResultVoPage.createCustomSuccess(1, ErrorCode.sys_error.SUCCESS_MSG, new ArrayList<>(), 0);
        } catch (Exception e) {
            log.error("店铺列表查询失败:" + e.getMessage());
        }
        return ResultVoPage.createCustomSuccess(1, ErrorCode.sys_error.SUCCESS_MSG, new ArrayList<>(), 0);
    }






    /**
     * 店铺页面
     * @param model
     * @return
     */
    @MenuAuthory
    @RequestMapping("/sysStorePage/{menu_code}")
    public String sysStorePage( Model model) {

        return view(model, Constant.Views.sysStorePage);
    }


    /**
     * 角色页面
     * @param model
     * @return
     */
    @MenuAuthory
    @RequestMapping("/sysRolePage/{menu_code}")
    public String sysRolePage( Model model) {

        return view(model, Constant.Views.sysRolePage);
    }



}
