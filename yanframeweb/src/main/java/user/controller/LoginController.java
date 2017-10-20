package user.controller;

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
@RequestMapping("LoginController")
@Controller
public class LoginController extends BaseController {

    @Autowired
    public TestService testService;


    /**
     * 登录页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/login")
    public String LoginPage(Model model) {
        return "login";
    }


    /**
     * @Author qxy
     * @Date: 2017/10/9 17:29
     */
    @RequestMapping(value = "/doLogin")
    @ResponseBody
    public ResultVo doLogin(SysUser sysUser, @RequestParam(required = false, defaultValue = "true") boolean rememberMe) {

        if (sysUser == null) {
            ResultVo.createCustomSuccess(ErrorCode.sys_error.FAIL_CODE, ErrorCode.sys_error.FAIL_MSG, null);
        }

        //帐号或者手机号无效
        if (StringUtils.isBlank(sysUser.getLogin_account())) {
            ResultVo.createCustomSuccess(ErrorCode.sys_error.FAIL_CODE, ErrorCode.sys_user.USERNAME_FAIL, null);
        }

        //密码无效
        if (StringUtils.isBlank(sysUser.getLogin_pass())) {
            ResultVo.createCustomSuccess(ErrorCode.sys_error.FAIL_CODE, ErrorCode.sys_user.PASS_FAIL, null);
        }

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getLogin_account(), sysUser.getLogin_pass(), rememberMe);
        try {
            subject.login(token);
            if (subject.isAuthenticated()) {
                //登录成功
                return ResultVo.createCustomSuccess(ErrorCode.sys_error.SUCCESS_CODE, ErrorCode.sys_error.SUCCESS_MSG, null);
            }
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            return ResultVo.createCustomSuccess(ErrorCode.sys_error.FAIL_CODE, ErrorCode.sys_user.PASS_FAIL, null);
        } catch (ExcessiveAttemptsException e) {
            e.printStackTrace();
            log.error("登录失败次数过多");
        } catch (LockedAccountException e) {
            StringBuilder msg = new StringBuilder("帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.");
            e.printStackTrace();
            log.error(msg.toString());
        } catch (DisabledAccountException e) {
            StringBuilder msg = new StringBuilder("帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.");
            e.printStackTrace();
            log.error(msg.toString());
        } catch (UnknownAccountException e) {
            StringBuilder msg = new StringBuilder("帐号不存在. There is no user with username of " + token.getPrincipal());
            e.printStackTrace();
            log.error(msg.toString());
        } catch (UnauthorizedException e) {
            StringBuilder msg = new StringBuilder("您没有得到相应的授权！" + e.getMessage());
            e.printStackTrace();
            log.error(msg.toString());
        }

        return ResultVo.createCustomSuccess(ErrorCode.sys_error.FAIL_CODE, ErrorCode.sys_error.FAIL_MSG, null);
    }


    /**
     * @Author qxy
     * @Date: 2017/10/9 17:29
     * 用户退出登录
     */
    @RequestMapping(value = "/doLogOut",method = RequestMethod.GET)
    public String doLogOut() {

        Subject subject = SecurityUtils.getSubject();

        try {
            if (subject.isAuthenticated()) {
                subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
                log.info("--------------用户退出登录--------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("用户：" + subject.getSession().getAttribute(Constant.SYSUSERDTO) + " 退出失败...");
        }

        return "login";
    }






    private Logger log = LoggerFactory.getLogger(LoginController.class);

}
