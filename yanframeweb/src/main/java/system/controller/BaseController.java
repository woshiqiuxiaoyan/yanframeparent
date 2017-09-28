package system.controller;

import exception.CustomException;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
    @ExceptionHandler
    public String exp(HttpServletRequest request, Exception ex) {

        ex.printStackTrace();


        CustomException customException = null;

        //解析全局错误
        if( ex instanceof CustomException){
            //用户自定义异常
            customException = (CustomException) ex;

        }else {
            //系统错误非自定义异常
            customException = new CustomException("未知错误");

        }

        String message = customException.getMessage();

        request.setAttribute("message",message);

        return "errorpage";

    }

}
