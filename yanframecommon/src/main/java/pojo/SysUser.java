package pojo;


import lombok.Data;

@Data
public class SysUser   {
    private java.lang.Integer user_id;//   主键
	private java.lang.String login_account;//   登录账号
	private java.lang.String login_pass;//   登录密码
	private java.lang.String user_name;//   昵称
	private java.lang.String user_head;//   头像
	private java.lang.String user_phone;//   手机
	private java.lang.String user_email;//   邮箱
	private java.lang.Integer user_sex;//   性别
	private java.util.Date user_birthday;//   生日
	private java.util.Date register_time;//   注册时间
	private java.lang.Integer create_by;//   创建者;对应sys_user(login_account)字段
	private java.lang.String real_name;//   真实姓名
}