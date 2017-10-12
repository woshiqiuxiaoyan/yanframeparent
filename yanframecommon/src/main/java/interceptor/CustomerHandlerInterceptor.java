package interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * <p>Title:CustomerHandlerInterceptor </p>
 * <p>Description:自定义spring 拦截器 ,记录所有进入的url参数进行打印</p>
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
        log.info("--------------------请求url:"+httpServletRequest.getRequestURI());
       /* Enumeration enu  = httpServletRequest.getParameterNames();
        log.info("--------------------请求参数如下：");
        while(null!=enu && enu.hasMoreElements()){
            String paraName=(String)enu.nextElement();
            log.info("  "+paraName+" : "+httpServletRequest.getParameter(paraName));
        }*/
        return true;
    }

    //进入Handler之后,返回ModelAndView之前
    //应用场景：从ModelAndView 发出：将公用的模型数据在这里传到视图（比如：菜单导航），也可以在这里统一指定视图
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }


    //进入Handler执行完后
    //应用场景：统一异常处理，统一日志处理
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
