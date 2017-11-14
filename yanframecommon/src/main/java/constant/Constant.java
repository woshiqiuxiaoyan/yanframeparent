package constant;

/**
 * <p>Title:Constant </p>
 * <p>Description:</p>
 * Created with IntelliJ IDEA.
 * User: qxy
 * Date: 2017/10/10
 * Time: 10:09
 */
public class Constant {

    public static String SYSUSERDTO = "SYSUSERDTO";//登录session 的 key 值

    public static String MENULIST = "MENULIST";//菜单列表session key 值

    public static String LAY_CONTAIN = "lay_contain"; //指定内容页面


    public static String INEXPAGE = "admin/index";//主页

    public static String PIC_URL_GOODS_UPLOAD = "uploadimages/GoodsInfoUpload/";//图片上传保存的真正路径

    public static String yanFrameParent_content_url="/"; //全局路径

    public static String yanFrameParent_real_url="/"; //全局真正路径

    public static String yanFrameParent_img_url="";//上传图片路径

    public static String yanFrameParent_real_img_url="";//上传图片真正路径


    public interface Views {
        String createCard = "admin/CustomManager/createcustomcard";//开卡页面
        String ctUserGrade = "admin/CustomManager/ctusergrademanager";//会员等级页面
        String firstPage = "admin/content";//首页页面
        String redirectFirstPage ="redirect:index";//重定向到首页
        String  customList="admin/CustomManager/customcardlist";//会员列表页面
        String createGoodsInfo = "admin/GoodsInfoManager/createsgoodsinfo";//产品入录页面
        String goodsInfoList = "admin/GoodsInfoManager/goodsinfolist";//产品入录页面
        String inStockPage="admin/StockManager/instockinfo";//进货页面
        String stockListPage="admin/StockManager/stocklist";//库存列表页面
        String  sysUserInfoList="admin/SysUserInfoManager/sysUserInfolist";//系统用户列表
        String  sysRolePage="admin/SysUserInfoManager/sysrolelist";//系统角色
        String  sysStorePage="admin/SysUserInfoManager/sysstorelist";//店铺页面
        String  consumePage="admin/OrderManager/quickconsume";//快速消费页面


    }

    public interface NorMsg{
         String CREATECARDREMARK="会员开卡";
    }

    public interface sys_user{
        String DEFAULT_PASSWORD ="666666";//初始化密码
        String ADD_SYSUSER_FAIL ="增加系统用户失败";
        String UPDATE_PERMISSION_FAIL ="系统用户权限更新失败";
        String ADD_ROLE_FAIL ="系统角色增加失败";
        String UPDATE_ROLE_FAIL ="系统角色更新失败";
        String DEL_ROLE_FAIL="删除角色失败";
        String ROLE_IS_USERING="该角色正在被使用，无法删除";

    }

    public interface sys_store{
        String ADD_STORE_FAIL ="增加店铺失败";//增加店铺失败
        String DEL_STORE_FAIL ="删除店铺失败";//删除店铺失败
        String UPDATE_STORE_FAIL ="更新店铺失败";//删除店铺失败
    }

    public  static int DefaultPageSize=5;//默认每页大小

}
