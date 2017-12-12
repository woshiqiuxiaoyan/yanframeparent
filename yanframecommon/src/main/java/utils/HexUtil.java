package utils;

import java.util.Locale;

/**
 * <p>Title:HexUtil </p>
 * <p>Description:</p>
 * Created with IntelliJ IDEA.
 * User: qxy
 * Date: 2017/11/30
 * Time: 10:05
 */
public class HexUtil {

    /**
     *  将byte转为16进制
     *  @param bytes
     *  @return
     */
    public static String byte2Hex(byte[] bytes) {
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




    /**
     * 十六进制字符串转换成 ASCII字符串
     *
     * @return String 对应的字符串
     */
    public static byte[] hexStr2Str(String hexStr) {
        char[] mChars = "0123456789ABCDEF".toCharArray();
        String mHexStr = "0123456789ABCDEF";

        hexStr = hexStr.toString().trim().replace(" ", "").toUpperCase(Locale.US);
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int iTmp = 0x00;
        ;

        for (int i = 0; i < bytes.length; i++) {
            iTmp = mHexStr.indexOf(hexs[2 * i]) << 4;
            iTmp |= mHexStr.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (iTmp & 0xFF);
        }
        return bytes;
    }


}
