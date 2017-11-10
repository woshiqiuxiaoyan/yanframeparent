package user.service;

import com.github.pagehelper.Page;
import user.dto.SysStoreDTO;
import user.dto.SysUserDTO;

import java.util.List;

public interface ISysStoreService {


    /**
     * 系统店铺列表
     * @param sysUser
     * @param sysStoreDTO
     * @return
     */
    Page<SysStoreDTO> getSysStoreList(SysUserDTO sysUser, SysStoreDTO sysStoreDTO);

    /**
     * 绑定用户列表下拉框
     * @return
     */
    List<SysUserDTO> getSysUserList();

    /**
     * 添加店铺
     * @param sysStoreDTO
     * @return
     */
    int addSysStore(SysStoreDTO sysStoreDTO);
}