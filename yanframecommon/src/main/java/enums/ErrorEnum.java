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
    ACCOUNT_NOT_EXIST(1001,"帐号不存在"),ROLE_ID_ERROR(1001,"角色异常"),;

    private int code ;
    private String message;

    ErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
