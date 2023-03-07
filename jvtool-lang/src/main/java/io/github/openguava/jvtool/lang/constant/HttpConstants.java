package io.github.openguava.jvtool.lang.constant;

/**
 * http 常量
 * @author openguava
 *
 */
public class HttpConstants {

	/** http状态码 100 客户端应当继续发送请求  */
	public static final int HTTP_STATUSCODE_CONTINUE = 100;
	
	/** http状态码 200 请求已成功 */
	public static final int HTTP_STATUSCODE_OK = 200;
	
	/** http状态码 301 被请求的资源已永久移动到新位置 */
	public static final int HTTP_STATUSCODE_MOVED_PERMANENTLY = 301;
	
	/** http状态码 400 语义有误，当前请求无法被服务器理解  */
	public static final int HTTP_STATUSCODE_BAD_REQUEST = 400;
	
	/** http状态码401 当前请求需要用户验证  */
	public static final int HTTP_STATUSCODE_UNAUTHORIZED = 401;
	
	/** http状态码403 服务器已经理解请求，但是拒绝执行它 */
	public static final int HTTP_STATUSCODE_FORBIDDEN = 403;
	
	/** http状态码 404 请求失败，请求所希望得到的资源未被在服务器上发现 */
	public static final int HTTP_STATUSCODE_NOT_FOUND = 404;
	
	/** http状态码 500 服务器遇到了一个未曾预料的状况，导致了它无法完成对请求的处理 */
	public static final int HTTP_STATUSCODE_INTERNAL_SERVER_ERROR = 500;
	
	/** http状态码 502 作为网关或者代理工作的服务器尝试执行请求时，从上游服务器接收到无效的响应 */
	public static final int HTTP_STATUSCODE_INTERNAL_BAD_GATEWAY = 502;
	
	/** http状态码503 由于临时的服务器维护或者过载，服务器当前无法处理请求 */
	public static final int HTTP_STATUSCODE_INTERNAL_SERVICE_UNAVAILABLE = 503;
	
	/** http状态码504 作为网关或者代理工作的服务器尝试执行请求时，未能及时从上游服务器（URI标识出的服务器，例如HTTP、FTP、LDAP）或者辅助服务器（例如DNS）收到响应 */
	public static final int HTTP_STATUSCODE_GATEWAY_TIMEOUT= 504;
	
	public static final String HTTP_REQUEST_METHOD_GET = "GET";
	
	public static final String HTTP_REQUEST_METHOD_POST = "POST";
	
	public static final String HTTP_REQUEST_METHOD_OPTIONS = "OPTIONS";
	
	public static final String HTTP_REQUEST_METHOD_DELETE = "DELETE";
	
	public static final String HTTP_REQUEST_METHOD_PUT = "PUT";
	
	public static final String HTTP_REQUEST_METHOD_HEAD = "HEAD";
	
	public static final String HTTP_REQUEST_METHOD_PATCH = "PATCH";
	
	public static final String HTTP_REQUEST_METHOD_TRACE = "TRACE";
	
	public static final String HTTP_CONTENTTYPE_TEXT_PLAIN = "text/plain";
	
	public static final String HTTP_CONTENTTYPE_TEXT_HTML = "text/html";
	
	public static final String HTTP_CONTENTTYPE_TEXT_HTML_UTF8 = HTTP_CONTENTTYPE_TEXT_HTML + ";charset=utf-8";
	
	public static final String HTTP_CONTENTTYPE_TEXT_CSS = "text/css";
	
	public static final String HTTP_CONTENTTYPE_TEXT_XML = "text/xml";
	
	public static final String HTTP_CONTENTTYPE_IMAGE_GIF = "image/gif";
	
	public static final String HTTP_CONTENTTYPE_IMAGE_JPEG = "image/jpeg";
	
	public static final String HTTP_CONTENTTYPE_IMAGE_PNG = "image/png";
	
	public static final String HTTP_CONTENTTYPE_MULTIPART_FORM_DATA = "multipart/form-data";
	
	public static final String HTTP_CONTENTTYPE_APPLICATION_JAVASCRIPT = "application/javascript";
	
	public static final String HTTP_CONTENTTYPE_APPLICATION_JSON = "application/json";
	
	public static final String HTTP_CONTENTTYPE_APPLICATION_JSON_UTF8 = HTTP_CONTENTTYPE_APPLICATION_JSON + ";charset=utf-8";
	
	public static final String HTTP_CONTENTTYPE_APPLICATION_PROBLEM_JSON = "application/problem+json";
	
	public static final String HTTP_CONTENTTYPE_APPLICATION_PROBLEM_JSON_UTF8 = HTTP_CONTENTTYPE_APPLICATION_PROBLEM_JSON + ";charset=UTF-8";
	
	public static final String HTTP_CONTENTTYPE_APPLICATION_PROBLEM_XML = "application/problem+xml";
	
	public static final String HTTP_CONTENTTYPE_APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
	
	/** 二进制流数据格式 */
	public static final String HTTP_CONTENTTYPE_APPLICATION_OCTET_STREAM = "application/octet-stream";
	
	public static final String HTTP_CONTENTTYPE_APPLICATION_XML = "application/xml";
	
	public static final String HTTP_CONTENTTYPE_APPLICATION_PDF = "application/pdf";
	
	public static final String HTTP_HEADER_ACCEPT = "Accept";
	
	public static final String HTTP_HEADER_ACCEPT_LANGUAGE = "Accept-Language";
	
	public static final String HTTP_HEADER_ACCEPT_ENCODING = "Accept-Encoding";
	
	public static final String HTTP_HEADER_ACCEPT_CHARSET = "Accept-Charset";
	
	public static final String HTTP_HEADER_ACCEPT_PATCH = "Accept-Patch";
	
	public static final String HTTP_HEADER_ACCEPT_RANGES = "Accept-Ranges";
	
	public static final String HTTP_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
	
	public static final String HTTP_HEADER_ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
	
	public static final String HTTP_HEADER_ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
	
	public static final String HTTP_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	
	public static final String HTTP_HEADER_ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
	
	public static final String HTTP_HEADER_ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
	
	public static final String HTTP_HEADER_ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
	
	public static final String HTTP_HEADER_ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
	
	public static final String HTTP_HEADER_AUTHORIZATION = "Authorization";
	
	public static final String HTTP_HEADER_WWW_AUTHENTICATE = "WWW-Authenticate";
	
	public static final String HTTP_HEADER_COOKIE = "Cookie";
	
	public static final String HTTP_HEADER_SET_COOKIE = "Set-Cookie";
	
	public static final String HTTP_HEADER_SET_COOKIE2 = "Set-Cookie2";
	
	public static final String HTTP_HEADER_SERVER = "Server";
	
	public static final String HTTP_HEADER_ORIGIN = "Origin";
	
	public static final String HTTP_HEADER_REFERER = "Referer";
	
	public static final String HTTP_HEADER_USER_AGENT = "User-Agent";
	
	public static final String HTTP_HEADER_CONTENT_LENGTH = "Content-Length";
	
	public static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
	
	public static final String HTTP_HEADER_CONTENT_ENCODING = "Content-Encoding";
	
	public static final String HTTP_HEADER_CONTENT_DISPOSITION = "Content-Disposition";
	
	public static final String HTTP_HEADER_CONTENT_LANGUAGE = "Content-Language";
	
	public static final String HTTP_HEADER_CONTENT_LOCATION = "Content-Location";
	
	public static final String HTTP_HEADER_CONTENT_RANGE = "Content-Range";
	
	public static final String HTTP_HEADER_X_REQUESTED_WITH = "X-Requested-With";
	
	public static final String HTTP_HEADER_UPGRADE = "Upgrade";
	
	public static final String HTTP_MIME_IMAGE_PNG = "image/png";
	
	public static final String HTTP_MIME_IMAGE_JPG = "image/jpg";
	
	public static final String HTTP_MIME_IMAGE_JPEG = "image/jpeg";

	public static final String HTTP_MIME_IMAGE_BMP = "image/bmp";
	
	public static final String HTTP_MIME_IMAGE_GIF = "image/gif";
	
	public static final String[] HTTP_MIME_IMAGE_EXTENSION = { "bmp", "gif", "jpg", "jpeg", "png" };
	
	public static final String[] HTTP_MIME_FLASH_EXTENSION = { "swf", "flv" };
	
	public static final String[] HTTP_MIME_MEDIA_EXTENSION = { "swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg",
            "asf", "rm", "rmvb" };
	
	public static final String[] HTTP_MIME_VIDEO_EXTENSION = { "mp4", "avi", "rmvb" };
}
