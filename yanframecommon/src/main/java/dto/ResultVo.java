package dto;

import lombok.Data;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.io.Serializable;


/**
 * 通用VO
 */
@Data
@ToString
public class ResultVo  implements Serializable
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
	private Object result;


	
	public ResultVo(){}
	
	public ResultVo(String msg)
	{
		this.msg = msg;
	}
	
	public ResultVo(Object result){
		this.result = result;
	}
	
	public ResultVo(int code, String msg)
	{
		this.code = code;
		this.msg = msg;
	}

	public ResultVo(int code, String msg, Object result)
	{
		this.code = code;
		this.msg = msg;
		this.result = result;
	}
	

	
	/**
	 * Convenient method to create GeneralVo
	 * @param code
	 * @param msg
	 * @return
	 */
	public static ResultVo create(int code, String msg){
		return new ResultVo(code,msg);
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
	public static <T> ResultVo createSuccess(int code, String msg , T t){
		return new ResultVo(code, msg, t);
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
	public static ResultVo createCustomSuccess(int code, String msg, Object result) {
		return new ResultVo(code, msg, result);
	}
	public static ResultVo createFail(int code,String msg){
		return new ResultVo(code, msg);
	}
	public static <T> ResultVo createFail(int code,String msg,T t){
		return new ResultVo(code, msg,t);
	}



}
