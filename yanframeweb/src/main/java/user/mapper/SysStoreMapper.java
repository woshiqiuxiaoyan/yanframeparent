package user.mapper;


import org.apache.ibatis.annotations.Param;
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
    List<SysUserDTO> queryAllSysUser(@Param("store_user_id") String store_user_id);

    /**
     * 增加店铺
     * @param sysStoreDTO
     * @return
     */
    int insertSysStore(SysStoreDTO sysStoreDTO);

    /**
     * 更新店铺
     * @param sysUserDTO
     * @return
     */
    int updateUserForSysStore(SysUserDTO sysUserDTO);

    /**
     * 删除店铺
     * @param sysStoreDTO
     * @return
     */
    int delSysStore(SysStoreDTO sysStoreDTO);

    /**
     * 更新店铺
     * @param sysStoreDTO
     * @return
     */
    int updateSysStore(SysStoreDTO sysStoreDTO);

    /**
     * 取店铺信息
     * @param sysStoreDTO
     * @return
     */
    SysStoreDTO selectSysStoreByStoreId(SysStoreDTO sysStoreDTO);


}
