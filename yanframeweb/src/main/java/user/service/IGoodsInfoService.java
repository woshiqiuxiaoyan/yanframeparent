package user.service;

import com.github.pagehelper.Page;
import user.dto.SysGoodsInfoDTO;
import user.dto.SysUserDTO; /***
 * 产品管理
 */

public interface IGoodsInfoService {


    /**
     * 产品入录
     * @param sysUserDTO
     * @param sysGoodsInfoDTO
     * @return
     */
    int saveGoodsInfo(SysUserDTO sysUserDTO, SysGoodsInfoDTO sysGoodsInfoDTO);

    /**
     * 取产品列表
     * @param sysUser
     * @param sysGoodsInfoDTO
     * @return
     */
    Page<SysGoodsInfoDTO> getGoodsInfoList(SysUserDTO sysUser, SysGoodsInfoDTO sysGoodsInfoDTO);

    /**
     * 删除产品
     * @param sysGoodsInfoDTO
     * @return
     */
    int delGoods(SysGoodsInfoDTO sysGoodsInfoDTO);

    /**
     * 更新产品
     *
     * @param sysUserDTO
     * @param sysGoodsInfoDTO
     * @return
     */
    int updateGoodsInfo(SysUserDTO sysUserDTO, SysGoodsInfoDTO sysGoodsInfoDTO);
}
