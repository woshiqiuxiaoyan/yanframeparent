package user.dto;


import lombok.Data;
import lombok.ToString;
import pojo.SysUser;

@Data
@ToString
public class SysUserDTO extends SysUser {

    private boolean isShopKeeper;//是否是店长

    private java.lang.Integer shopkeeper_user_id;// 所属店长user_id
}