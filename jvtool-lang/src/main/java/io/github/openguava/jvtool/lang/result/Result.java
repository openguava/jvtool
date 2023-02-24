package io.github.openguava.jvtool.lang.result;

import java.io.Serializable;

import io.github.openguava.jvtool.lang.constant.StringConstants;

/**
 * 抽象 Result 对象
 * @author openguava
 *
 * @param <T>
 */
public abstract class Result<T> implements IResult<T>, Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Integer code = IResult.STATUS_CODE_OK;
	
	@Override
	public Integer getCode() {
		return this.code;
	}
	
	@Override
	public void setCode(Integer code) {
		this.code = code;
	}
	
	protected String msg = StringConstants.STRING_EMPTY;

	@Override
	public String getMsg() {
		return this.msg;
	}
	
	@Override
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	protected T data;

	@Override
	public T getData() {
		return this.data;
	}
	
	@Override
	public void setData(T data) {
		this.data = data;
	}
	
	public Result() {
		
	}
	
	public Result(T data, int code, String msg) {
		this.setResult(data, code, msg);
	}
}
