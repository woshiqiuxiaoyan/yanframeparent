package myhandlermethodargumentresolver;

import annotation.CurrentUser;
import constant.Constant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import user.dto.SysUserDTO;

/**
 * <p>Title:CurrentUserHandlerMethodArgumentResolver </p>
 * <p>Description:</p>
 * Created with IntelliJ IDEA.
 * User: qxy
 * Date: 2017/10/12
 * Time: 18:05
 */
public class CurrentUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {


    public boolean supportsParameter(MethodParameter parameter) {//有注解currentuser头部
        return parameter.hasParameterAnnotation(CurrentUser.class);
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Subject subject = SecurityUtils.getSubject();

        if(subject==null)
            return null;

        SysUserDTO sysUserDTO = (SysUserDTO) subject.getSession().getAttribute(Constant.SYSUSERDTO);
        return sysUserDTO;
    }

}
