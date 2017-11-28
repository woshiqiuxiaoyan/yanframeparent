package system.controller;

import constant.Constant;
import constant.ErrorCode;
import dto.ResultVo;
import exception.CustomException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Title:BaseController </p>
 * <p>Description:控制层基类</p>
 * Created with IntelliJ IDEA.
 * User: qxy
 * Date: 2017/8/18
 * Time: 18:08
 */
public class BaseController {

    /** 基于@ExceptionHandler异常处理 */
    /**
     * 处理权限异常
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler({UnauthorizedException.class, AuthorizationException.class })
    @ResponseBody
    public ResultVo exp(HttpServletRequest request, Exception ex) {
        CustomException customException = null;
        ex.printStackTrace();
        //解析全局错误
        if( ex instanceof UnauthorizedException){

            return ResultVo.createSuccess(ErrorCode.sys_error.FAIL_CODE,ErrorCode.sys_error.NOT_PERMISSION,ex.getMessage());
        }else {
            //系统错误非自定义异常
            customException = new CustomException("未知错误");
        }

        return ResultVo.createSuccess(ErrorCode.sys_error.FAIL_CODE,ErrorCode.sys_error.FAIL_MSG,customException.getMessage());
    }

    /**
     * 打开指定页面
     *
     * @param model
     * @param container
     * @return
     */
    public String view(Model model, String container) {
        model.addAttribute(Constant.LAY_CONTAIN, container);
        return Constant.INEXPAGE;
    }


}
