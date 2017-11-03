package user.service;


import com.github.pagehelper.Page;
import exception.CustomException;
import user.dto.CtUserGradeDTO;
import user.dto.CtUserInfoDTO;
import user.dto.SysStockRecordDTO;
import user.dto.SysUserDTO;

import java.util.List;

public interface ICtUserInfoService {


    /**
     * 会员开卡
     *
     * @param sysUser
     * @param ctUserInfoDTO
     * @return
     */
    int creatCard(SysUserDTO sysUser, CtUserInfoDTO ctUserInfoDTO) throws CustomException;

    /**
     * 会员列表查询
     *
     * @param sysUser
     * @param ctUserInfoDTO
     * @return
     */
    Page<CtUserInfoDTO> ctuserList(SysUserDTO sysUser, CtUserInfoDTO ctUserInfoDTO);

    /**
     * 更新会员信息
     *
     * @param sysUser
     * @param ctUserInfoDTO
     * @return
     */
    int updateCtUserInfo(SysUserDTO sysUser, CtUserInfoDTO ctUserInfoDTO);

    /**
     * 删除会员
     *
     * @param ctUserInfoDTO
     * @return
     */
    int delCtuser(CtUserInfoDTO ctUserInfoDTO);


    /**
     * 判断当前用户是不是店长或者管理员
     * @param sysUser
     * @return
     */
    boolean isShopKeeper(SysUserDTO sysUser);


    /**
     * 查询会员等级列表
     * @param ctUserGradeDTO
     * @return
     */
    Page<CtUserGradeDTO> getCtUserGradeList(CtUserGradeDTO ctUserGradeDTO);
}
