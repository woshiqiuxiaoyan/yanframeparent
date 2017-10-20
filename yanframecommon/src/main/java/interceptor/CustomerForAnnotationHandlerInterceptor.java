package interceptor;

import annotation.HelloYan;
import annotation.MenuAuthory;
import annotation.TrimSpace;
import constant.Constant;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pojo.SysAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>Title:CustomerHandlerInterceptor </p>
 * <p>Description:自定义spring 拦截器</p>
 * Created with IntelliJ IDEA.
 * User: qxy
 * Date: 2017/8/22
 * Time: 13:15
 */
public class CustomerForAnnotationHandlerInterceptor implements HandlerInterceptor {

    private Logger log = LoggerFactory.getLogger("CustomerForAnnotationHandlerInterceptor");


    //进入Handler之前
    //应用场景：比如登录拦截
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        //return false 拦截、return true 放行
        log.info("--------------------对注解时行拦截--------------------");

        if (handler instanceof HandlerMethod) {
            HandlerMethod handler2 = (HandlerMethod) handler;
            //菜单权限
            MenuAuthory menuAuthory = handler2.getMethodAnnotation(MenuAuthory.class);


            if (null != menuAuthory) {

                Subject subject = SecurityUtils.getSubject();
                Session session = subject.getSession();
                List<SysAuthority> menuList = (List<SysAuthority>) session.getAttribute(Constant.MENULIST);
                String[] urls = httpServletRequest.getRequestURI().split("/");
                String tmp = urls[urls.length - 1];
                Predicate<SysAuthority> contain = n -> n.getMenu_code().equals(tmp);
                List<SysAuthority> sysAuthorities = menuList.stream().filter(contain).collect(Collectors.toList());
                ;
                if (null != sysAuthorities && sysAuthorities.size() > 0) {
                    return true;
                } else {
                    return false;
                }

            }
        }

        /*


        Class<?> clazz = handler2.getBeanType();
        //获取controller注解
        HelloYan helloYanController = clazz.getAnnotation(HelloYan.class);
        // 获取方法注解


        if(null!=helloYanMethod){
            log.info("加了helloYanMethod注解");
        }

        if(null!=helloYanController){
            log.info("加了helloYanController注解");
        }


        return true;*/
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
