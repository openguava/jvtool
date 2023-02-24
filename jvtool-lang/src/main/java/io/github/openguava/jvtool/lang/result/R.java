package io.github.openguava.jvtool.lang.result;

/**
 * R 响应信息主体
 *
 * @author openguava
 */
public class R<T> extends Result<T> {
	
	private static final long serialVersionUID = 1L;

	public static <T> R<T> setOk() {
		return newResult(null, IResult.STATUS_CODE_OK, IResult.MSG_OK);
	}

	public static <T> R<T> setOk(T data) {
		return newResult(data, IResult.STATUS_CODE_OK, IResult.MSG_OK);
	}

	public static <T> R<T> setOk(T data, String msg) {
		return newResult(data, IResult.STATUS_CODE_OK, msg);
	}

	public static <T> R<T> setFail() {
		return newResult(null, IResult.STATUS_CODE_ERROR, IResult.MSG_ERROR);
	}

	public static <T> R<T> setFail(String msg) {
		return newResult(null, IResult.STATUS_CODE_ERROR, msg);
	}

	public static <T> R<T> setFail(T data) {
		return newResult(data, IResult.STATUS_CODE_ERROR, IResult.MSG_ERROR);
	}

	public static <T> R<T> setFail(T data, String msg) {
		return newResult(data, IResult.STATUS_CODE_ERROR, msg);
	}

	public static <T> R<T> setFail(int code, String msg) {
		return newResult(null, code, msg);
	}

	private static <T> R<T> newResult(T data, int code, String msg) {
		R<T> result = new R<>();
		result.setResult(data, code, msg);
		return result;
	}
}