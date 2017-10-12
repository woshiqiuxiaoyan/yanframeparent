package user.controller;

import annotation.CurrentUser;
import annotation.MenuAuthory;
import constant.Constant;
import constant.ErrorCode;
import dto.ResultVo;
import exception.CustomException;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import pojo.SimplePojo;
import pojo.SysUser;
import pojo.TUserInfo;
import system.controller.BaseController;
import user.controller.validation.VaildatorGroup1;
import user.dto.CtUserInfoDTO;
import user.dto.TDemo;
import user.service.TestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by t on 2017/8/16.
 */
@RequestMapping("IndexController")
@Controller
public class IndexController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public TestService testService;


    @RequestMapping("/index")
    public String enterTestHello3(Model model) {
        return view(model, Constant.Views.firstPage);
    }


    /**
     * 会员开卡页面
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
     * 新增会员
     * @param ctUserInfoDTO
     * @param model
     * @return
     */
    @RequestMapping("/createCtUserInfo")
    public String createCtUserInfo(@CurrentUser SysUser sysUser, CtUserInfoDTO ctUserInfoDTO, Model model) {



        log.info(sysUser.toString());


        log.info(ctUserInfoDTO.toString());

        return view(model, Constant.Views.createCard);
    }




}
