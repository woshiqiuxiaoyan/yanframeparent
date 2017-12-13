package enums;

/**
 * <p>Title:ErrorEnum </p>
 * <p>Description:</p>
 * Created with IntelliJ IDEA.
 * User: qxy
 * Date: 2017/12/13
 * Time: 18:13
 */
public enum ErrorEnum {
    LOGIN_ERROR("LOGIN_ERROR","帐号不存在");

    private String code ;
    private String message;

    ErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
