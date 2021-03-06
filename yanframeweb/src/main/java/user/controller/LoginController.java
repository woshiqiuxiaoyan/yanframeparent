package user.controller;

import constant.Constant;
import constant.ErrorCode;
import dto.ResultVo;
import enums.ErrorEnum;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.SysUser;
import system.controller.BaseController;
import user.service.TestService;

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
    @ApiOperation(nickname = "swagger-helloworld", value = "Swagger的世界", notes = "测试HelloWorld")
    public ResultVo doLogin(SysUser sysUser, @RequestParam(required = false, defaultValue = "true") boolean rememberMe) {

        if (sysUser == null) {
            return   ResultVo.createCustomSuccess(ErrorCode.sys_error.FAIL_CODE, ErrorCode.sys_error.FAIL_MSG, null);
        }

        //帐号或者手机号无效
        if (StringUtils.isBlank(sysUser.getLogin_account())) {
            return   ResultVo.createCustomSuccess(ErrorCode.sys_error.FAIL_CODE, ErrorCode.sys_user.USERNAME_FAIL, null);
        }

        //密码无效
        if (StringUtils.isBlank(sysUser.getLogin_pass())) {
          return  ResultVo.createCustomSuccess(ErrorCode.sys_error.FAIL_CODE, ErrorCode.sys_user.PASS_FAIL, null);
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
            StringBuilder msg = new StringBuilder("帐号已被锁定:" + token.getPrincipal()  );
            e.printStackTrace();
            log.error(msg.toString());
        } catch (DisabledAccountException e) {
            StringBuilder msg = new StringBuilder("帐号已被禁用:" + token.getPrincipal() );
            e.printStackTrace();
            log.error(msg.toString());
        } catch (UnknownAccountException e) {
            StringBuilder msg = new StringBuilder("帐号不存在:" + token.getPrincipal());
            log.error(msg.toString(),e);
            return ResultVo.createCustomSuccess(ErrorEnum.ACCOUNT_NOT_EXIST.getCode(),ErrorEnum.ACCOUNT_NOT_EXIST.getMessage());
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
