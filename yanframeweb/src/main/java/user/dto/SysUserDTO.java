package user.dto;


import lombok.Data;
import lombok.ToString;
import org.joda.time.DateTime;
import pojo.SysUser;

import java.io.Serializable;

@Data
@ToString
public class SysUserDTO extends SysUser  {

    private String user_birthday_form;//   生日

    private String role_value;//角色名称

    private String create_by_user_name;//创建人姓名

    private String store_name;//所属店铺名称

    private int page;

    private int limit;

    private DateTime start_time;

    private DateTime end_time;

    private Integer role_id;//角色ID
}