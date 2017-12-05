package user.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import exception.CustomException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import pojo.SysStore;
import pojo.TUserInfo;
import sun.net.www.http.HttpClient;
import system.controller.BaseController;
import user.controller.validation.VaildatorGroup1;
import user.dto.TDemo;
import user.service.TestService;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by t on 2017/8/16.
 */
@RequestMapping("TestController")
@Controller
public class TestController extends BaseController {

    @Autowired
    public TestService testService;

    /**
     * @param model
     * @param req
     * @return
     * @Authod
     * @Method 测试model
     */
    @RequestMapping(value = "/testFirst")
    public String testFirst(Model model, HttpServletRequest req) {
        model.addAttribute("aa", "123123");
        return "admin/CustomManager/1";
    }


    /**
     * 测试 dao
     *
     * @param td
     * @param model
     * @return
     */
    @RequestMapping(value = "/testDao", produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<TDemo> testDao(TDemo td, Model model, HttpServletRequest request) throws UnsupportedEncodingException {

        log.error(td.getUserName() + new String(td.getUserName().getBytes("iso-8859-1"), "UTF-8"));
        log.info(td.toString());
        List<TDemo> list = testService.queryList();
        return list;
    }


    /**
     * 测试 dao事务
     *
     * @param td
     * @param model
     * @return
     */
    @RequestMapping(value = "/testInsertDao", produces = "application/json;charset=utf-8")
    @ResponseBody
    public TDemo testInsertDao(TDemo td, Model model) {

        log.info("入参：" + td.toString());
        int affect = testService.insertTdemo(td);
        return td;
    }


    /**
     * 测试数据绑定
     *
     * @param td
     * @param resp
     */
    @RequestMapping(value = "/testBound", method = RequestMethod.POST)
    public void testBound(@RequestParam(value = "aa", required = true, defaultValue = "默认值") String td, String bb, HttpServletResponse resp) {
        try {

            System.out.println(td + " " + bb);
            resp.getWriter().print(td + "   " + bb);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 测试自定义 时间类型转换器
     *
     * @param t
     * @param resp
     */
    @RequestMapping(value = "/testDemo", method = RequestMethod.POST)
    public void testDemo(TDemo t, HttpServletResponse resp) {
        try {

            DateTime dateTime = t.getCreateTime();
            System.out.println(dateTime.toString("yyyy-MM-dd"));
            resp.getWriter().print(t);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 测试  spring vildation 校验测试
     *
     * @param t
     * @param resp
     */
    @RequestMapping(value = "/testVildator", method = {RequestMethod.POST, RequestMethod.GET})
    public void testVildator(@Validated TDemo t, BindingResult bindingResult, HttpServletResponse resp) {
        try {

            if (bindingResult.hasErrors()) {

                int lenght = bindingResult.getAllErrors().size();

                for (int i = 0; i < lenght; i++) {

                    String aa = bindingResult.getAllErrors().get(i).getDefaultMessage();

                    aa = new String(aa.getBytes("ISO-8859-1"), "utf-8");

                    System.out.println(aa);

                }

            }

            resp.getWriter().print(t);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 测试  spring vildation 分给校验001
     *
     * @param t
     * @param resp
     */
    @RequestMapping(value = "/testVildatorGroup001", method = {RequestMethod.POST, RequestMethod.GET})
    public void testVildatorGroup001(@Validated(value = VaildatorGroup1.class) TDemo t, BindingResult bindingResult, HttpServletResponse resp) {
        try {
            TUserInfo userInfo = new TUserInfo();

            if (bindingResult.hasErrors()) {

                int lenght = bindingResult.getAllErrors().size();

                for (int i = 0; i < lenght; i++) {

                    String aa = bindingResult.getAllErrors().get(i).getDefaultMessage();

                    aa = new String(aa.getBytes("ISO-8859-1"), "utf-8");

                    System.out.println(aa);

                }

            }

            resp.getWriter().print(t);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //测试 页面直接取
    @ModelAttribute("tdemotest")
    public TDemo getModeAttribute() {

        TDemo t = new TDemo();
        t.setUserName("你好啊");

        return t;
    }


    /**
     * 测试  spring 默认回显数据
     *
     * @param t
     * @ModelAttribute 回显的key
     */
    @RequestMapping(value = "/testRepairDisplay", method = {RequestMethod.POST, RequestMethod.GET})
    public String testRepairDisplay(@ModelAttribute(value = "items") TDemo t, BindingResult bindingResult, Model model) {
        try {

            System.out.println(t);

            model.addAttribute("msg", "返回成功");

        } catch (Exception e) {
            e.printStackTrace();
        }


        return "subform";

    }


    /**
     * 测试  spring 全局异常处理器 CustomExceptionResolver
     *
     * @param t
     * @ModelAttribute 回显的key
     */
    @RequestMapping(value = "/testCustomExceptionResolver", method = {RequestMethod.POST, RequestMethod.GET})
    public String testCustomExceptionResolver(@ModelAttribute(value = "items") TDemo t, BindingResult bindingResult, Model model) throws CustomException {

        System.out.println(t);

        model.addAttribute("msg", "返回成功");
        if (true) {
//            测试用户自定义异常
//            throw new CustomException("测试异常");
        }
//            测试系统自定义异常
        int i = 1 / 0;

        return "subform";

    }


    /**
     * A
     * 测试  图片上传
     *
     * @param
     * @ModelAttribute 回显的key
     */
    @RequestMapping(value = "/testUploadPic", method = {RequestMethod.POST, RequestMethod.GET})
    public String testUploadPic(@RequestParam("testFile") MultipartFile uploadFile, HttpServletRequest request) throws CustomException, IOException {

        String originFileName = uploadFile.getOriginalFilename();

        //前半部分路径
        String leftPath = request.getRealPath("/uploadimages");

        File file = new File(leftPath, originFileName);

        uploadFile.transferTo(file);

        return "subform";

    }


    /**
     * A
     * 测试  图片上传
     *
     * @param
     * @ModelAttribute 回显的key
     */
    @RequestMapping(value = "/testUploadPic1", method = {RequestMethod.POST, RequestMethod.GET})
    public String testUploadPic1(MultipartFile uploadFile, HttpServletRequest request) throws CustomException, IOException {

        String originFileName = uploadFile.getOriginalFilename();

        //前半部分路径
        String leftPath = request.getRealPath("/uploadimages");

        File file = new File(leftPath, originFileName);

        uploadFile.transferTo(file);

        return "subform";

    }


    /**
     * A
     * 测试  json数据转送
     *
     * @param
     * @ModelAttribute 回显的key
     */
    @RequestMapping(value = "/testJsonTranserf", method = {RequestMethod.POST, RequestMethod.GET})
//,produces = "application/json")
    public @ResponseBody
    Map<String, Object> testJsonTranserf(@RequestBody Map<String, Object> map) throws Exception, IOException {
        log.info("我是测试json");
        return map;
    }


    /**
     * A
     * 测试  restful 支持
     *
     * @param
     * @ModelAttribute 回显的key
     */
    @RequestMapping(value = "/testRestFul/{userName}/{age}", method = {RequestMethod.POST, RequestMethod.GET})
//,produces = "application/json")
    public @ResponseBody
    SysStore testRestFul(@PathVariable("userName") String username, @PathVariable("age") int age) throws Exception, IOException {
        SysStore smpojo = new SysStore();
        smpojo.setStore_name(username);
        smpojo.setStore_id(age);
        return smpojo;

    }


    private Logger log = LoggerFactory.getLogger(TestController.class);

    /**
     * A
     * 测试  日志
     *
     * @param
     * @ModelAttribute 回显的key
     */
    @RequestMapping(value = "/testLog/{userName}", method = {RequestMethod.POST, RequestMethod.GET})
//,produces = "application/json")
    public @ResponseBody
    SysStore testLog(@PathVariable("userName") String username) throws Exception, IOException {
        log.debug("试试...");
        SysStore smpojo = new SysStore();
        smpojo.setStore_name(username);
        return smpojo;

    }

    /**
     * A
     * 测试 maven相互依赖
     *
     * @param
     * @ModelAttribute 回显的key
     */
    @RequestMapping(value = "/testMaven", method = {RequestMethod.POST, RequestMethod.GET})
//,produces = "application/json")
    public @ResponseBody
    TUserInfo testMaven() throws Exception, IOException {
        log.debug("试试...");
        TUserInfo smpojo = new TUserInfo();

        return smpojo;

    }


    @RequestMapping("/test111")
    public ModelAndView enterTestHello(Model model) {
        model.addAttribute("yan", "yan");
        ModelAndView modelAndView = new ModelAndView("layuitest");
        modelAndView.addObject("name", "123");
        return modelAndView;
    }

    @RequestMapping("/test222")
    public ModelAndView enterTestHello1(Model model) {
        model.addAttribute("yan", "yan");
        ModelAndView modelAndView = new ModelAndView("layuitest");
        modelAndView.addObject("name", "123");
        return modelAndView;
    }


    @RequestMapping("/test333")
    public ModelAndView enterTestHello3(Model model) {
        model.addAttribute("yan", "yan");

        ModelAndView modelAndView = new ModelAndView("admin/index");
        modelAndView.addObject("name", "123");
        return modelAndView;
    }

    final String HashKey = "7J08gqtUGigZaZRYsSihZ3hR6tmNImPZ";
    final String HashIV = "EMV2aHnnX9vTQ0lj";





    @RequestMapping("/hello/{data}")
    public String helloTest(@PathVariable("data") String data,@RequestParam("email") String email, Model model) {

        String requestUrl = "https://ccore.spgateway.com/MPG/mpg_gateway";


        String PaymentType = "CREDIT";


        String MerchantID = "MS32779620";//商店代號

        String TradeInfo = "交易資料AES 加密";//交易資料AES 加密

        String TradeSha = "";//交易資料 SHA256 加密 再次加密

        String Version = "1.4";


        String RespondType = "JSON";


        String TimeStamp = String.valueOf(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));


        String LangType = "zh-tw";//語系

        String MerchantOrderNo = "201406010001";//商店訂單編號

        String Amt = "1";//訂單金額 新台币

        String ItemDesc = "测试商品";//商品資訊 utf-8  50字


        String TradeLimit = "800";//交易限制秒數 60-9000 或者0 不限制

        String ExpireDate = "20140620";//繳費有效期限 date('Ymd') ，例：20140620

        String ReturnURL = "";

        String NotifyURL = "";

        String CustomerURL = "";

        String ClientBackURL = "";

        String Email = "";

        String EmailModify = "1";//付款人電子信箱 是否開放修改

        String LoginType = "0";//1 = 須要登入智付通會員


        String CREDIT = "1";//設定是否啟用信用卡一次付清支付方式 1 启用


        Map<String, Object> map = new HashMap<>();


        log.info("---------------------拼接交易资料----------------------");
        map.put("MerchantID", MerchantID);
        map.put("RespondType", "JSON");
        map.put("TimeStamp", LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
        map.put("Version", 1.4);
        map.put("MerchantOrderNo", "S_20171124_qxy001");
        map.put("Amt", 1);
        map.put("ItemDesc", "商品描述");
        map.put("Email",email);
        map.put("LoginType", 0);
        map.put("CREDIT", 1);






        String encrp = pingjieAse(map);

        map.put("TradeInfo", encrp);

        String shs = "HashKey=" + HashKey + "&" + encrp + "&HashIV=" + HashIV;

        TradeSha = getSHA256StrJava(shs).toUpperCase();

        map.put("TradeSha", TradeSha);


        model.addAttribute("MerchantID", map.get("MerchantID"));
        model.addAttribute("TradeInfo", encrp);
        model.addAttribute("TradeSha", TradeSha);
        model.addAttribute("Version", 1.4);




        return "TestHello";
    }



    @RequestMapping("/hello1")
    public String hello1(  Model model) {

        return "TestHello1";
    }


        /**
         * 　　* 利用java原生的摘要实现SHA256加密
         * 　　* @param str 加密后的报文
         * 　　* @return
         *
         */
    public static String getSHA256StrJava(String str) {

        System.out.println("SHA256:"+str);
        MessageDigest messageDigest = null;

        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println(encodeStr);
        return encodeStr;
    }

    /**
     * 　　* 将byte转为16进制
     * 　　* @param bytes
     * 　　* @return
     *
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    public String pingjieAse(Map<String, Object> map) {
        String content = "";// "Amt=40&MerchantID=3430112&MerchantOrderNo=S_1485232229&RespondType=JSON&TimeStamp=1485232229&Version=1.4";

        content = mapCompare(map).toString();

        String aa = encrypt(content, "utf-8", HashKey, HashIV);
        System.out.println("加密后：" + aa);
        return aa;
    }


    /**
     * @param map Map
     * @return String
     * @Method描述: Map排序
     * @创建时间: 2015年11月6日上午8:34:44
     */
    public static Map mapCompare(Map map) {

        // 对Map进行排序后得出sign
        // Key为 sign, Value为 null 的都不参与签名
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            String result = String.valueOf(it.next().getValue());
            if (result == null || "".equals(result) || "null".equals(result)) {
                it.remove();
            }
        }
        Map treeMap = new TreeMap() {
            public String toString() {
                Iterator iterator = this.entrySet().iterator();
                StringBuffer sb = new StringBuffer();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    String key = entry.getKey().toString();
                    Object value = entry.getValue();
                    sb.append(key + '=' + value + '&');
                }
                return sb.substring(0, sb.length() - 1).toString();
            }
        };
        treeMap.putAll(map);
        return treeMap;
    }


    /**
     * 加密 aes cbc
     *
     * @param content
     * @param encodingFormat utf-8
     * @param HashKey
     * @param HashIV
     * @return
     */
    public static String encrypt(String content, String encodingFormat, String HashKey, String HashIV) {
        try {

            System.out.println("加密前：" + content);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 创建密码器

            byte[] raw = HashKey.getBytes();

            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

            //使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec iv = new IvParameterSpec(HashIV.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(content.getBytes(encodingFormat));

            return byte2HexStr(encrypted, encrypted.length).toLowerCase();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        return "";
    }

    private final static char[] mChars = "0123456789ABCDEF".toCharArray();
    private final static String mHexStr = "0123456789ABCDEF";

    public static String byte2HexStr(byte[] b, int iLen) {
        StringBuilder sb = new StringBuilder();
        for (int n = 0; n < iLen; n++) {
            sb.append(mChars[(b[n] & 0xFF) >> 4]);
            sb.append(mChars[b[n] & 0x0F]);
        }
        return sb.toString().trim().toUpperCase(Locale.US);
    }


}
