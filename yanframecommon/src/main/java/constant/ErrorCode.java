package constant;

/**
 * <p>Title:ErrorCode </p>
 * <p>Description:</p>
 * Created with IntelliJ IDEA.
 * User: qxy
 * Date: 2017/10/9
 * Time: 16:27
 */
public class ErrorCode {


    public static interface sys_error {
        Integer SUCCESS_CODE = 1000;//系统默认成功
        Integer FAIL_CODE = 1001;//失败
        String SUCCESS_MSG="操作成功";
        String FAIL_MSG="操作失败";
        String PARAM_FAIL="参数错误";
    }


    public static interface sys_user {
        String USERNAME_FAIL="用户名无效";
        String PASS_FAIL="密码错误";
        String NO_LOGIN_ERROR = "请先登录";
        String SYS_ROLE_ERROR = "用户角色异常";
        String CARD_NO_ERROR = "会员卡号有误";
        String CREATE_CARD_ERROR = "开卡失败";
    }

    /**
     * 会员开卡
     */
    public static interface create_card{
        String REFEREE_CARD_NO_EXIST="推荐人卡号有误";
        String REAL_NAME_ERROR = "真实姓名不合法";
        String CARD_USERD_ERROR = "该卡号已经被使用过了";
        String CARD_USER_UPDATE_FAIL = "会员更新失败";

    }

    /**
     * 创建产品
     */
    public static interface create_GoodsInfo{
        String GOODS_ID_FAIL="货号不无效";
        String GOODS_NAME_FAIL="商品名称无效";
        String GOODS_INSTOCK_PRICE_FAIL="进货价格有误";
        String CREATE_GOODSINFO_FAIL="创建产品失败";
        String GOODSINFO_NO_EXIST="产品不存在";
    }

    /**
     * 库存操作
     */
    public static interface manager_Stock{
        String INSTOCK_FAIL="商品入库失败";
        String INSTOCK_GOODS_NUM_FAIL="入库商品数量不能为0";

    }



}
