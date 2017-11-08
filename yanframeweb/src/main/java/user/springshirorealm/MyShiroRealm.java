package user.springshirorealm;

import constant.Constant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import user.dto.SysAuthorityDTO;
import user.dto.SysRolePermissionDTO;
import user.dto.SysUserDTO;
import user.service.IAccountService;

import java.util.List;

/**
 * <p>Title:MyShiroRealm </p>
 * <p>Description:</p>
 * Created with IntelliJ IDEA.
 * User: qxy
 * Date: 2017/10/9
 * Time: 9:48
 */
public class MyShiroRealm extends AuthorizingRealm {


    // 支持什么类型的token
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    /***
     * 获取授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {

        //取菜单权限
        System.out.println(getName() + " ------------------------");
        SysUserDTO sysUserDTO = (SysUserDTO) pc.fromRealm(getName()).iterator().next();
        List<SysRolePermissionDTO> sysRolePermissionDTOList = accountService.getRolePermission(sysUserDTO);
        if (sysRolePermissionDTOList != null && sysRolePermissionDTOList.size() > 0) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            sysRolePermissionDTOList.stream().forEach(
                    (string) -> {
                        info.addStringPermission(string.getPermissions());//权限
                    }
            );
            return info;
        }
        return null;
    }

    /***
     * 获取认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken at) {


        UsernamePasswordToken token = (UsernamePasswordToken) at;
        // 通过表单接收的用户名
        String username = (String) at.getPrincipal();

        SysUserDTO sysUser = new SysUserDTO();
        sysUser.setLogin_account(username);

        if (null == token.getPassword()) {
            return null;
        }

        String tmp = String.valueOf(token.getPassword());
        sysUser.setLogin_pass(tmp);
        sysUser = accountService.login(sysUser);
        if (sysUser != null) {
            //查询对应菜单权限
            List<SysAuthorityDTO> sysAuthorityDTOList = accountService.getMenuList(sysUser);
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            session.setAttribute(Constant.SYSUSERDTO, sysUser);//用户信息存入session 中
            session.setAttribute(Constant.MENULIST, sysAuthorityDTOList);//菜单信息存入session 中
            setName(sysUser.getUser_id() + "");
            System.out.println(getName());
            return new SimpleAuthenticationInfo(sysUser, sysUser.getLogin_pass(), getName());
        }
        return null;
    }


    /**
     * 用户的业务类
     **/
    @Autowired
    private IAccountService accountService;


}
