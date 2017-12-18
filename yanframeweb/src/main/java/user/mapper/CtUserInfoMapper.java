package user.mapper;

import org.springframework.stereotype.Repository;
import user.dto.CtOrdersDTO;
import user.dto.CtUserGradeDTO;
import user.dto.CtUserInfoDTO;

import java.util.List;

@Repository
public interface CtUserInfoMapper  {


    /**
     * 保存会员开卡
     * @param ctUserInfoDTO
     * @return
     */
    int saveCtUserInfo(CtUserInfoDTO ctUserInfoDTO);

    /**
     * 卡号查询会员
     * @param card_no
     * @return
     */
    CtUserInfoDTO queryCtUserInfoByCardNo(String card_no);


    /**
     * 查询当前用店铺下的会员列表
     * @param ctUserInfoDTO
     * @return
     */
    List<CtUserInfoDTO> queryByCondition(CtUserInfoDTO ctUserInfoDTO);

    /**
     * 会员更新
     * @param ctUserInfoDTO
     * @return
     */
    int updateCtUserInfo(CtUserInfoDTO ctUserInfoDTO);

    /**
     * 删除会员
     * @param ctUserInfoDTO
     * @return
     */
    int delCtuser(CtUserInfoDTO ctUserInfoDTO);

    /**
     * 查询会员等级
     * @param ctUserGradeDTO
     * @return
     */
    List<CtUserGradeDTO> queryCtUserGradeList(CtUserGradeDTO ctUserGradeDTO);

    /**
     * 增加会员等级
     * @param ctUserGradeDTO
     * @return
     */
    int insertCtUserGrade(CtUserGradeDTO ctUserGradeDTO);

    /**
     * 修改会员等级
     * @param ctUserGradeDTO
     * @return
     */
    int updateCtUserGradeById(CtUserGradeDTO ctUserGradeDTO);

    /**
     * 删除会员等级
     * @param id
     * @return
     */
    int delCtUserGrade(Integer id);

    /**
     * 更新用户积分
     * @param ctOrdersDTO
     * @return
     */
    int updateCtUserIntegral(CtOrdersDTO ctOrdersDTO);

    /**
     * 通过 id 取会员 信息
     * @param ct_user_info_id
     * @return
     */
    CtUserInfoDTO queryCtUserCardNo(Integer ct_user_info_id);
}
