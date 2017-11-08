package myhandlermethodargumentresolver;

import annotation.CurrentUser;
import constant.Constant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import user.dto.SysUserDTO;
import user.service.ICtUserInfoService;

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

        SysUserDTO sysUser = (SysUserDTO) subject.getSession().getAttribute(Constant.SYSUSERDTO);


      /*  //查询当前用店长（店铺）的会员列表
        if (ctUserInfoService.isShopKeeper(sysUser)) {
            //当前 店长 或者 管理员登录则 将自己设置成为会员所属店铺
            sysUser.setShopkeeper_user_id(sysUser.getUser_id());
            sysUser.setShopKeeper(true);
        } else {
            //普通员工取自己的创建者（店长）
            sysUser.setShopkeeper_user_id(sysUser.getCreate_by());
            sysUser.setShopKeeper(true);
        }*/
        return sysUser;
    }

}
