package user.controller;

import com.alibaba.fastjson.JSONObject;
import dto.MsgPushEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * PushDemoController
 * 推送服务消息接收示例
 * 依赖SPRING 3.0或以上版本
 * @auther ChengZi
 * @data 16/9/10
 */

@Controller
@RequestMapping("/test")
public class PushDemoController {


    private static final int mode = 1 ; //服务商
    private static final String clientId="b6fad97dfe5c4cf241"; //服务商的秘钥证书
    private static final String clientSecret="61d014fc7d7529c0e16bc2732072a52a";//服务商的秘钥证书
    private static final Logger log = LoggerFactory.getLogger(PushDemoController.class);



    @RequestMapping(value = "/getMshPushEntity" )
    @ResponseBody
    public Object getMshPushEntity(@RequestBody MsgPushEntity entity) {

        return getObject(entity);
    }

    private Object getObject(MsgPushEntity entity) {
        JSONObject res = new JSONObject();
        res.put("code", 0);
        res.put("msg", "success");


        /**
         *  判断是否为心跳检查消息
         *  1.是则直接返回
         */
        if (entity.isTest()) {
            return res;
        }

        /**
         * 解析消息推送的模式  这步判断可以省略
         * 0-商家自由消息推送 1-服务商消息推送
         * 以服务商 举例
         * 判断是否为服务商类型的消息
         * 否则直接返回
         */
        if (entity.getMode() != mode ){
            return res;
        }

        /**
         * 判断消息是否合法
         * 解析sign
         * MD5 工具类开发者可以自行引入
         */
        String sign= null;
        try {
            sign = MD5(clientId+entity.getMsg()+clientSecret);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        /**
         * 对于msg 先进行URI解码
         */
        String msg="";
        try {
            msg= URLDecoder.decode(entity.getMsg(), "utf-8");
            log.info("业务消息 msg ："+msg);
            log.info("业务消息 entity ："+entity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (!sign.equals(entity.getSign())){
            return res;
        }


        /**
         *  ..........
         *  接下来是一些业务处理
         *  判断当前消息的类型 比如交易
         *
         */

        if ("TRADE".equals(entity.getType())) {


            //TODO: 参考文档对应的交易对象 进行JSON解码  业务处理等


        }


        /**
         * 返回结果
         */
        return res;
    }


    @RequestMapping(value = "/getMshPushEntityNoRequestBody" )
    @ResponseBody
    public Object getMshPushEntityNoRequestBody( MsgPushEntity entity) {

        return getObject(entity);
    }




    public static String MD5(String originString ) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        if(StringUtils.isBlank(originString))
            return "";

        String result = "";
        // 指定加密的方式为MD5
        MessageDigest md = MessageDigest.getInstance("MD5");
        // 进行加密运算
        byte bytes[] = md.digest(originString.getBytes("ISO8859-1"));
        for (int i = 0; i < bytes.length; i++) {
            // 将整数转换成十六进制形式的字符串 这里与0xff进行与运算的原因是保证转换结果为32位
            String str = Integer.toHexString(bytes[i] & 0xFF);
            if (str.length() == 1) {
                str += "F";
            }
            result += str;
        }
        return result ;
    }



}
