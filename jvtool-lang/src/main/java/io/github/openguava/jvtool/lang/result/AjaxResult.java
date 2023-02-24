package io.github.openguava.jvtool.lang.result;

import java.util.HashMap;

/**
 * ajax 结果信息
 * @author openguava
 *
 */
public class AjaxResult extends HashMap<String, Object> implements IResult<Object> {

	private static final long serialVersionUID = 1L;
	
	/** 状态码 */
	public static final String KEY_CODE = "code";

	/** 返回内容 */
	public static final String KEY_MSG = "msg";

	/** 数据对象 */
	public static final String KEY_DATA = "data";

	@Override
	public Integer getCode() {
		Object value = this.get(KEY_CODE);
		return (value instanceof Integer) ? (int)value : null;
	}
	
	@Override
	public void setCode(Integer code) {
		this.put(KEY_CODE, code);
	}

	@Override
	public String getMsg() {
		Object value = this.get(KEY_MSG);
		return value == null ? null : value.toString();
	}
	
	@Override
	public void setMsg(String msg) {
		this.put(KEY_MSG, msg);
	}

	@Override
	public Object getData() {
		return this.get(KEY_DATA);
	}
	
	@Override
	public void setData(Object data) {
		this.put(KEY_DATA, data);
	}
	
	/**
	 * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
	 */
	public AjaxResult() {
		
	}
	
	public AjaxResult(Object data, int code, String msg) {
		this.setResult(data, code, msg);
	}
	
	@Override
	public Object get(Object key) {
		return super.get(key);
	}
	
	/**
	 * 方便链式调用
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public AjaxResult put(String key, Object value) {
		super.put(key, value);
		return this;
	}
	
	public static AjaxResult setOk() {
		return newResult(null, IResult.STATUS_CODE_OK, IResult.MSG_OK);
	}

	public static <T> AjaxResult setOk(T data) {
		return newResult(data, IResult.STATUS_CODE_OK, IResult.MSG_OK);
	}

	public static <T> AjaxResult setOk(T data, String msg) {
		return newResult(data, IResult.STATUS_CODE_OK, msg);
	}

	public static AjaxResult setFail() {
		return newResult(null, IResult.STATUS_CODE_ERROR, IResult.MSG_ERROR);
	}

	public static AjaxResult setFail(String msg) {
		return newResult(null, IResult.STATUS_CODE_ERROR, msg);
	}

	public static <T> AjaxResult setFail(T data) {
		return newResult(data, IResult.STATUS_CODE_ERROR, IResult.MSG_ERROR);
	}

	public static <T> AjaxResult setFail(T data, String msg) {
		return newResult(data, IResult.STATUS_CODE_ERROR, msg);
	}

	public static AjaxResult setFail(int code, String msg) {
		return newResult(null, code, msg);
	}
	
	private static <T> AjaxResult newResult(T data, int code, String msg) {
		AjaxResult result = new AjaxResult();
		result.setResult(data, code, msg);
		return result;
	}
}
