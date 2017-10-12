package converter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by t on 2017/8/17.
 * 自定义DateTime类型转化
 */
@Component("customDateTimeConverter")
public class CustomDateTimeConverter implements Converter<String, DateTime> {


    public DateTime convert(String str) {

        System.out.println("***********************************CustomDateTimeConverter***********************************");

        LoggerFactory.getLogger(this.getClass()).info(str);

        DateTime dateTime = null;

        String []tmp =str.split(":");
        System.out.println("--------------------"+tmp.length);
        if (null != str && str.split("-").length == 3 && str.split(":").length == 3) {
            DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            dateTime = DateTime.parse(str, format);
        }
        if (null != str && str.split("-").length == 3 && str.split(":").length == 4) {
            DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss:SSS");
            dateTime = DateTime.parse(str, format);
        }

        if (null != str && str.split("-").length == 3 && str.indexOf(":")<0) {
            DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
            dateTime = DateTime.parse(str, format);
        }

        System.out.println(dateTime);
        return dateTime;
    }


}
