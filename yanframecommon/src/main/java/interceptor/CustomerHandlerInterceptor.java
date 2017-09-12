package interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Title:CustomerHandlerInterceptor </p>
 * <p>Description:自定义spring 拦截器</p>
 * Created with IntelliJ IDEA.
 * User: qxy
 * Date: 2017/8/22
 * Time: 13:15
 */
public class CustomerHandlerInterceptor implements HandlerInterceptor {

    private Logger log = LoggerFactory.getLogger("enterUrl");
    //进入Handler之前
    //应用场景：比如登录拦截
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //return false 拦截、return true 放行

         log.info(httpServletRequest.getRequestURI());
        return true;
    }

    //进入Handler之后,返回ModelAndView之前
    //应用场景：从ModelAndView 发出：将公用的模型数据在这里传到视图（比如：菜单导航），也可以在这里统一指定视图
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        log.info("我是拦截器postHandle");
    }


    //进入Handler执行完后
    //应用场景：统一异常处理，统一日志处理
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
