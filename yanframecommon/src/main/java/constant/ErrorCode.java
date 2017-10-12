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
    }


    public static interface sys_user {
        String USERNAME_FAIL="用户名无效";
        String PASS_FAIL="密码错误";
    }



}
