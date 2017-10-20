package user.controller;

import annotation.CurrentUser;
import annotation.MenuAuthory;
import annotation.TrimSpace;
import com.github.pagehelper.Page;
import constant.Constant;
import constant.ErrorCode;
import dto.ResultVo;
import dto.ResultVoPage;
import exception.CustomException;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.ExecutionException;
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
import user.dto.SysUserDTO;
import user.dto.TDemo;
import user.service.ICtUserInfoService;
import user.service.TestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
    public ICtUserInfoService ctUserInfoService;


    @RequestMapping("/index")
    public String enterTestHello3(Model model) {
        return view(model, Constant.Views.firstPage);
    }




}
