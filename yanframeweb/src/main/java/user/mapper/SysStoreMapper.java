package user.mapper;


import org.springframework.stereotype.Repository;
import user.dto.SysStoreDTO;
import user.dto.SysUserDTO;

import java.util.List;

@Repository
public interface SysStoreMapper {

    /**
     * 查询店铺列表
     * @param sysStoreDTO
     * @return
     */
    List<SysStoreDTO> getSysStoreList(SysStoreDTO sysStoreDTO);

    /**
     * 查询不是店长的系统用户列表
     * @return
     */
    List<SysUserDTO> queryAllSysUser();
}
