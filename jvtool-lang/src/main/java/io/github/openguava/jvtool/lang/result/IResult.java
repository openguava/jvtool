package io.github.openguava.jvtool.lang.result;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.github.openguava.jvtool.lang.constant.HttpConstants;

/**
 * Result 接口
 * @author openguava
 *
 * @param <T>
 */
public interface IResult<T> {
	
	/** 200 成功 */
	public static int STATUS_CODE_OK = HttpConstants.HTTP_STATUSCODE_OK;
	
	/** 400 错误请求 */
	public static int STATUS_CODE_BAD_REQUEST = HttpConstants.HTTP_STATUSCODE_BAD_REQUEST;
	
	/** 401 未授权(未登录) */
	public static int STATUS_CODE_UNAUTHORIZED = HttpConstants.HTTP_STATUSCODE_UNAUTHORIZED;
	
	/** 403 访问受限，授权过期(无权限) */
	public static int STATUS_CODE_FORBIDDEN = HttpConstants.HTTP_STATUSCODE_FORBIDDEN;
	
	/** 500 操作失败 */
	public static int STATUS_CODE_ERROR = HttpConstants.HTTP_STATUSCODE_INTERNAL_SERVER_ERROR;
	
	public static String MSG_OK = "操作成功";
	
	public static String MSG_ERROR = "操作失败";
	
	/**
	 * 获取 code
	 * @return
	 */
	Integer getCode();
	
	/**
	 * 设置 code
	 * @param code
	 */
	void setCode(Integer code);
	
	/**
	 * 获取 msg
	 * @return
	 */
	String getMsg();
	
	/**
	 * 设置 msg
	 * @param msg
	 */
	void setMsg(String msg);
	
	/**
	 * 获取 data
	 * @return
	 */
	T getData();
	
	/**
	 * 设置 data
	 * @param data
	 */
	void setData(T data);
	
	/**
	 * 是否成功
	 * @return
	 */
	//@JsonIgnore
	public default boolean isSuccess() {
		Integer code = this.getCode();
		return code != null && code.equals(STATUS_CODE_OK);
	}
	
	/**
	 * 是否错误
	 * @return
	 */
	//@JsonIgnore
	public default boolean isError() {
		return !this.isSuccess();
	}
	
	/**
	 * 成功
	 */
	public default IResult<T> ok() {
		this.setResult(null, STATUS_CODE_OK, null);
		return this;
	}
	
	/**
	 * 成功
	 * @param msg 消息
	 */
	public default IResult<T> ok(String msg) {
		this.setResult(null, STATUS_CODE_OK, msg);
		return this;
	}
	
	/**
	 * 成功
	 * @param data 数据
	 * @param msg 消息
	 */
	public default IResult<T> ok(T data, String msg) {
		this.setResult(data, STATUS_CODE_OK, msg);
		return this;
	}
	
	/**
	 * 失败
	 */
	public default IResult<T> fail() {
		this.setResult(null, STATUS_CODE_ERROR, null);
		return this;
	}
	
	/**
	 * 失败
	 * @param msg 消息
	 */
	public default IResult<T> fail(String msg) {
		setResult(null, STATUS_CODE_ERROR, msg);
		return this;
	}
	
	/**
	 * 失败
	 * @param data 数据
	 * @param msg 消息
	 */
	public default IResult<T> fail(T data, String msg) {
		this.setResult(data, STATUS_CODE_ERROR, msg);
		return this;
	}
	
	/**
	 * 失败
	 * @param data 数据
	 * @param msg 消息
	 */
	public default IResult<T> fail(int code, String msg) {
		this.setResult(null, code, msg);
		return this;
	}
	
	/**
	 * 未授权
	 * @param msg 消息
	 */
	public default IResult<T> unauthorized(String msg) {
		this.setResult(null, STATUS_CODE_UNAUTHORIZED, msg);
		return this;
	}
	
	/**
	 * 未授权
	 * @param throwable 异常信息
	 */
	public default IResult<T> unauthorized(Throwable throwable) {
		this.setResult(null, STATUS_CODE_UNAUTHORIZED, throwable != null ? throwable.getMessage() : null);
		return this;
	}
	
	/**
	 * 未授权
	 * @param data 数据
	 * @param msg 消息
	 */
	public default IResult<T> unauthorized(T data, String msg) {
		this.setResult(data, STATUS_CODE_UNAUTHORIZED, msg);
		return this;
	}
	
	/**
	 * 禁止
	 * @param msg 消息
	 */
	public default IResult<T> forbidden(String msg) {
		this.setResult(null, STATUS_CODE_FORBIDDEN, msg);
		return this;
	}

	/**
	 * 禁止
	 * @param data 数据
	 * @param msg 消息
	 */
	public default IResult<T> forbidden(T data, String msg) {
		this.setResult(data, STATUS_CODE_FORBIDDEN, msg);
		return this;
	}
	
	/**
	 * 设置结果
	 * @param data
	 * @param code
	 * @param msg
	 * @return
	 */
	public default IResult<T> setResult(T data, int code, String msg) {
		this.setCode(code);
		this.setMsg(msg);
		this.setData(data);
		return this;
	}
}
