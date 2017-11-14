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
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.SysUser;
import system.controller.BaseController;
import user.dto.SysStockDTO;
import user.dto.SysStockRecordDTO;
import user.dto.SysStoreDTO;
import user.dto.SysUserDTO;
import user.service.ISysStockService;
import user.service.ISysStoreService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t on 2017/8/16.
 *
 * @Author qxy
 */
@RequestMapping("SysStoreController")
@org.springframework.stereotype.Controller
public class SysStoreController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ISysStoreService sysStoreService;


    /**
     * 店铺列表(管理员才能看)
     *
     * @param sysStoreDTO
     * @return
     */
    @RequestMapping("/getSysStoreList")
    @ResponseBody
    public ResultVoPage getSysStoreList(@CurrentUser SysUserDTO sysUser, SysStoreDTO sysStoreDTO) {


        try {

            Page<SysStoreDTO> sysStoreDTOPage = sysStoreService.getSysStoreList(sysUser, sysStoreDTO);

            if (null != sysStoreDTOPage && sysStoreDTOPage.size() > 0) {
                return ResultVoPage.createCustomSuccess(0, ErrorCode.sys_error.SUCCESS_MSG, sysStoreDTOPage, sysStoreDTOPage.getTotal());
            }
        } catch (CustomException e) {
            log.error("店铺列表查询:" + e.getMessage());
            return ResultVoPage.createCustomSuccess(1, ErrorCode.sys_error.SUCCESS_MSG, new ArrayList<>(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("店铺列表查询失败:" + e.getMessage());
        }
        return ResultVoPage.createCustomSuccess(1, ErrorCode.sys_error.SUCCESS_MSG, new ArrayList<>(), 0);
    }



    /**
     * 用户列表绑定下拉框
     *
     * @return
     */
    @RequestMapping("/getSysUserList")
    @ResponseBody
    public ResultVo getSysUserList(String store_user_id) {
        List<SysUserDTO> sysUserDTOList = sysStoreService.getSysUserList(store_user_id);
        return ResultVo.createSuccess(ErrorCode.sys_error.SUCCESS_CODE,ErrorCode.sys_error.SUCCESS_MSG,sysUserDTOList);
    }


    /**
     * 增加店铺
     *
     * @return
     */
    @RequiresPermissions("addSysStore")
    @RequestMapping("/addSysStore")
    @ResponseBody
    public ResultVo addSysStore(@CurrentUser SysUserDTO sysUserDTO,SysStoreDTO sysStoreDTO) {

        try {
            int effect =  sysStoreService.addSysStore(sysStoreDTO);
            if(effect!=0){
                return ResultVo.createSuccess(ErrorCode.sys_error.SUCCESS_CODE,ErrorCode.sys_error.SUCCESS_MSG,null);
            }
        }catch (CustomException e){
            log.error("增加店铺失败:"+e.getMessage());
            return ResultVo.createSuccess(ErrorCode.sys_error.FAIL_CODE,e.getMessage(),null);
        }
        catch (Exception e) {
            log.error("增加店铺失败:"+e.getMessage());
        }
        return ResultVo.createSuccess(ErrorCode.sys_error.FAIL_CODE,ErrorCode.sys_error.FAIL_MSG,null);
    }



    /**
     * 更新店铺
     *
     * @return
     */
    @RequestMapping("/updateSysStore")
    @ResponseBody
    public ResultVo updateSysStore(SysStoreDTO sysStoreDTO) {

        try {
            int effect = sysStoreService.updateSysStore(sysStoreDTO);
            if(effect!=0){
                return ResultVo.createSuccess(ErrorCode.sys_error.SUCCESS_CODE,ErrorCode.sys_error.SUCCESS_MSG,null);
            }
        }catch (CustomException e){
            e.printStackTrace();
            return ResultVo.createSuccess(ErrorCode.sys_error.FAIL_CODE,e.getMessage(),null);
        }catch (Exception e) {
            return ResultVo.createSuccess(ErrorCode.sys_error.FAIL_CODE,ErrorCode.sys_error.FAIL_MSG,null);
        }
        return ResultVo.createSuccess(ErrorCode.sys_error.FAIL_CODE,ErrorCode.sys_error.FAIL_MSG,null);
    }






}
