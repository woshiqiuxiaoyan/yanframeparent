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

    public static String yanFrameParent_url="http://localhost:8080/";


    public interface Views {
        public static String createCard = "admin/CustomManager/createcustomcard";//开卡页面
        public static String firstPage = "admin/content";//首页页面
        public static String redirectFirstPage ="redirect:index";//重定向到首页
        public static String  customList="admin/CustomManager/customcardlist";//会员列表页面
        public static String createGoodsInfo = "admin/GoodsInfoManager/createsgoodsinfo";//产品入录页面
        public static String goodsInfoList = "admin/GoodsInfoManager/goodsinfolist";//产品入录页面
        public static String inStockPage="admin/StockManager/instockinfo";//进货页面


    }

    public interface NorMsg{
        public static String CREATECARDREMARK="会员开卡";
    }

    public  static int DefaultPageSize=5;//默认每页大小

}
