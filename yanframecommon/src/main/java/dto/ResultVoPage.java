package dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 * 通用VO
 */
@Data
@ToString
public class ResultVoPage implements Serializable
{
	private static final long serialVersionUID = 1L;



	/**
	 * code == 0时表示没有错误 其余表示错误
	 */
	private int code;
	/**
	 * 错误原因
	 */
	private String msg;
	/**
	 * 返回结果 json
	 * */
	private Object data;

	private long count;


	public ResultVoPage(){}

	public ResultVoPage(String msg)
	{
		this.msg = msg;
	}

	public ResultVoPage(Object data){
		this.data = data;
	}

	public ResultVoPage(int code, String msg)
	{
		this.code = code;
		this.msg = msg;
	}

	public ResultVoPage(int code, String msg, Object data)
	{
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public ResultVoPage(int code, String msg, Object data,long count)
	{
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.count = count;
	}

	
	/**
	 * Convenient method to create GeneralVo
	 * @param code
	 * @param msg
	 * @return
	 */
	public static ResultVoPage create(int code, String msg){
		return new ResultVoPage(code,msg);
	}
	
	
	
	/**
	 * @创建人: ZYC
	 * @Method描述: 返回成功
	 * @创建时间: 2016-6-23下午6:19:36
	 * @param code
	 * @param msg
	 * @param t
	 * @return
	 */
	public static <T> ResultVoPage createSuccess(int code, String msg , T t){
		return new ResultVoPage(code, msg, t);
	}
	
	/**
	 * @创建人: ZYC
	 * @Method描述: 自定义返回成功
	 * @创建时间: 2016-6-27下午2:07:07
	 * @param code
	 * @param msg
	 * @param result
	 * @return
	 */
	public static ResultVoPage createCustomSuccess(int code, String msg, Object result) {
		return new ResultVoPage(code, msg, result);
	}

	public static ResultVoPage createCustomSuccess(int code, String msg, Object result,long count) {
		return new ResultVoPage(code, msg, result,count);
	}

	public static ResultVoPage createFail(int code, String msg){
		return new ResultVoPage(code, msg);
	}
	public static <T> ResultVoPage createFail(int code, String msg, T t){
		return new ResultVoPage(code, msg,t);
	}



}
