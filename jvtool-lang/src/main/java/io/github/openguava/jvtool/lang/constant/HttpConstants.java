package io.github.openguava.jvtool.lang.constant;

/**
 * http 常量
 * @author openguava
 *
 */
public class HttpConstants {

	/** http状态码 200 */
	public static final int HTTP_STATUSCODE_OK = 200;
	
	/** http状态码 400 错误请求 */
	public static final int HTTP_STATUSCODE_BAD_REQUEST = 400;
	
	/** http状态码401 未授权 */
	public static final int HTTP_STATUSCODE_UNAUTHORIZED = 401;
	
	/** http状态码403 */
	public static final int HTTP_STATUSCODE_FORBIDDEN = 403;
	
	/** http状态码 404 */
	public static final int HTTP_STATUSCODE_NOT_FOUND = 404;
	
	/** http状态码 500 内部错误 */
	public static final int HTTP_STATUSCODE_INTERNAL_SERVER_ERROR = 500;
	
	/** http状态码 502 */
	public static final int HTTP_STATUSCODE_INTERNAL_BAD_GATEWAY = 502;
	
	/** http状态码503 */
	public static final int HTTP_STATUSCODE_INTERNAL_SERVICE_UNAVAILABLE = 503;
	
	public static final String HTTP_REQUEST_METHOD_GET = "GET";
	
	public static final String HTTP_REQUEST_METHOD_POST = "POST";
	
	public static final String HTTP_REQUEST_METHOD_OPTIONS = "OPTIONS";
	
	public static final String HTTP_CONTENTTYPE_TEXT_HTML = "text/html";
	
	public static final String HTTP_CONTENTTYPE_TEXT_HTML_UTF8 = HTTP_CONTENTTYPE_TEXT_HTML + ";charset=utf-8";
	
	public static final String HTTP_CONTENTTYPE_TEXT_CSS = "text/css";
	
	public static final String HTTP_CONTENTTYPE_APPLICATION_JAVASCRIPT = "application/javascript";
	
	public static final String HTTP_CONTENTTYPE_APPLICATION_JSON = "application/json";
	
	public static final String HTTP_CONTENTTYPE_APPLICATION_JSON_UTF8 = HTTP_CONTENTTYPE_APPLICATION_JSON + ";charset=utf-8";
	
	/** 二进制流数据格式 */
	public static final String HTTP_CONTENTTYPE_APPLICATION_OCTET_STREAM = "application/octet-stream";
	
	public static final String HTTP_HEADER_ORIGIN = "Origin";
	
	public static final String HTTP_HEADER_REFERER = "Referer";
	
	public static final String HTTP_HEADER_USER_AGENT = "User-Agent";
	
	public static final String HTTP_HEADER_CONTENT_LENGTH = "Content-Length";
	
	public static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
	
	public static final String HTTP_HEADER_CONTENT_DISPOSITION = "Content-Disposition";
	
	public static final String HTTP_HEADER_X_REQUESTED_WITH = "X-Requested-With";
	
	public static final String HTTP_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
	
	public static final String HTTP_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
}
