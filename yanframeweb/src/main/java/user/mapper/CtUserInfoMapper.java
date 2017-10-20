package user.mapper;

import org.springframework.stereotype.Repository;
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
}
