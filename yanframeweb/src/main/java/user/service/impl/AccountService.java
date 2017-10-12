package user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pojo.SysUser;
import user.dto.SysAuthorityDTO;
import user.dto.SysUserDTO;
import user.dto.TDemo;
import user.mapper.SysUserMapper;
import user.mapper.TDemoMapper;
import user.service.IAccountService;

import java.util.List;

/**
 * <p>Title:AccountService </p>
 * <p>Description:</p>
 * Created with IntelliJ IDEA.
 * User: qxy
 * Date: 2017/10/9
 * Time: 9:54
 */
@Service
public class AccountService implements IAccountService {

    @Autowired
    private SysUserMapper sysUserMapper;



    public SysUserDTO login(SysUserDTO sysUser) {
        return  sysUserMapper.querySysUserByPwdAndUserName(sysUser);
    }

    public List<SysAuthorityDTO> getMenuList(SysUserDTO sysUser) {
        return sysUserMapper.getAutorityListByUserId(sysUser);
    }

}
