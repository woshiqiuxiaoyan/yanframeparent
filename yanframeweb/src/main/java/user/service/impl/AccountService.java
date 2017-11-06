package user.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import constant.ErrorCode;
import exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pojo.SysStore;
import pojo.SysUser;
import user.dto.*;
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

    /**
     * 获取当前用户菜单列表
     * @param sysUser
     * @return
     */
    public List<SysAuthorityDTO> getMenuList(SysUserDTO sysUser) {
        return sysUserMapper.getAutorityListByUserId(sysUser);
    }

    /**
     * 获取用户角色
     * @param sysUser
     * @return
     */
    public SysRoleDTO getUserRole(SysUserDTO sysUser) {
        return sysUserMapper.getUserRole(sysUser);
    }

    /**
     * 获取系统用户列表
     * @param sysUser
     * @param sysUserDTO
     * @return
     */
    @Override
    public Page<SysUserDTO> getSysUserInfoList(SysUserDTO sysUser, SysUserDTO sysUserDTO) {
        if (null == sysUser || null == sysUser.getUser_id()) {
            //未登录异常
            throw new CustomException(ErrorCode.sys_user.NO_LOGIN_ERROR);
        }

        sysUserDTO.setStore_id(sysUser.getStore_id());
        
        Page<SysUserDTO> sysUserDTOPage = PageHelper.startPage(sysUserDTO.getPage(), sysUserDTO.getLimit())
                .doSelectPage(() -> sysUserMapper.queryByCondition(sysUserDTO));


        return sysUserDTOPage;

    }

    /**
     * 查询角色列表
     * @param sysUser
     * @param sysRoleDTO
     * @return
     */
    @Override
    public Page<SysRoleDTO> getSysRoleList(SysUserDTO sysUser, SysRoleDTO sysRoleDTO) {

        Page<SysRoleDTO> sysRoleDTOPage = PageHelper.startPage(sysRoleDTO.getPage(), sysRoleDTO.getLimit())
                .doSelectPage(() -> sysUserMapper.querySysRoleByCondition(sysRoleDTO));
        return sysRoleDTOPage;
    }

    /**
     * 查询店铺信息
     * @param sysUser
     * @param sysStoreDTO
     * @return
     */
    @Override
    public Page<SysStoreDTO> getSysStoreList(SysUserDTO sysUser, SysStoreDTO sysStoreDTO) {
        Page<SysStoreDTO> sysStoreDTOPage = PageHelper.startPage(sysStoreDTO.getPage(), sysStoreDTO.getLimit())
                .doSelectPage(() -> sysUserMapper.getSysStoreList(sysStoreDTO));
        return sysStoreDTOPage;
    }


}
