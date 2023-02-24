package io.github.openguava.jvtool.lang.result;

/**
 * 返回结果信息
 *
 * @author openguava
 */
public class ApiResult<T> extends Result<T> {

	private static final long serialVersionUID = 1L;

	public static <T> ApiResult<T> setOk() {
		return newResult(null, IResult.STATUS_CODE_OK, IResult.MSG_OK);
	}

	public static <T> ApiResult<T> setOk(T data) {
		return newResult(data, IResult.STATUS_CODE_OK, IResult.MSG_OK);
	}

	public static <T> ApiResult<T> setOk(T data, String msg) {
		return newResult(data, IResult.STATUS_CODE_OK, msg);
	}

	public static <T> ApiResult<T> setFail() {
		return newResult(null, IResult.STATUS_CODE_ERROR, IResult.MSG_ERROR);
	}

	public static <T> ApiResult<T> setFail(String msg) {
		return newResult(null, IResult.STATUS_CODE_ERROR, msg);
	}

	public static <T> ApiResult<T> setFail(T data) {
		return newResult(data, IResult.STATUS_CODE_ERROR, IResult.MSG_ERROR);
	}

	public static <T> ApiResult<T> setFail(T data, String msg) {
		return newResult(data, IResult.STATUS_CODE_ERROR, msg);
	}

	public static <T> ApiResult<T> setFail(int code, String msg) {
		return newResult(null, code, msg);
	}

	private static <T> ApiResult<T> newResult(T data, int code, String msg) {
		ApiResult<T> result = new ApiResult<>();
		result.setResult(data, code, msg);
		return result;
	}
}