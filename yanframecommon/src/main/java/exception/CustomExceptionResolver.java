package exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Title:CustomExceptionResolver </p>
 * <p>Description:自定义全局异常处理器 (废弃用  @ExceptionHandler 替代)</p>
 * Created with IntelliJ IDEA.
 * User: qxy
 * Date: 2017/8/18
 * Time: 17:21
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {

    /**
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param handler
     * @param e 系统抛出的异常
     * @return
     */
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) {

        CustomException customException = null;

        //解析全局错误
        if( e instanceof CustomException){
            //用户自定义异常
            customException = (CustomException) e;

        }else {
            //系统错误非自定义异常
            customException = new CustomException("未知错误");

        }

        String message = customException.getMessage();

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("message",message);

        modelAndView.setViewName("errorpage");

        return modelAndView;
    }
}
