package pojo;

import lombok.Data;
import org.joda.time.DateTime;

/**
 * <p>Title:TUserInfo </p>
 * <p>Description:</p>
 * Created with IntelliJ IDEA.
 * User: qxy
 * Date: 2017/8/28
 * Time: 10:23
 */
@Data
public class TUserInfo {

    private int id;
    private String userName;
    private DateTime updateTime;
    private DateTime createTime;

}
