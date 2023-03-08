package io.github.openguava.jvtool.lang.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import io.github.openguava.jvtool.lang.constant.CharsetConstants;
import io.github.openguava.jvtool.lang.constant.HttpConstants;
import io.github.openguava.jvtool.lang.constant.StringConstants;
import io.github.openguava.jvtool.lang.constant.UrlConstants;
import io.github.openguava.jvtool.lang.exception.UtilException;
import io.github.openguava.jvtool.lang.text.AntPathMatcher;

/**
 * servlet 工具类
 * @author openguava
 *
 */
public class ServletUtils {

	private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
	
	/** 默认字符集 */
	public static String DEFAULT_CHARSET = CharsetConstants.UTF_8;
	
	/** 默认缓冲区大小 */
	public static int DEFAULT_BUFFER_SIZE = 1024 * 1024;
	
	/** Request Supplier */
	private static volatile Supplier<HttpServletRequest> requestSupplier;
	
	public static Supplier<HttpServletRequest> getRequestSupplier() {
		return requestSupplier;
	}
	
	public static void setRequestSupplier(Supplier<HttpServletRequest> requestSupplier) {
		synchronized (ServletUtils.class) {
			ServletUtils.requestSupplier = requestSupplier;
		}
	}
	
	/** Response Supplier */
	private static volatile Supplier<HttpServletResponse> responseSupplier;
	
	public static Supplier<HttpServletResponse> getResponseSupplier() {
		return responseSupplier;
	}
	
	public static void setResponseSupplier(Supplier<HttpServletResponse> responseSupplier) {
		synchronized (ServletUtils.class) {
			ServletUtils.responseSupplier = responseSupplier;
		}
	}
	
	/**
	 * 获取当前 servlet 请求
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		Supplier<HttpServletRequest> supplier = getRequestSupplier();
		if(supplier != null) {
			return supplier.get();
		}
		// 兼容 spring
		Object springRequestAttributes = getSpringRequestAttributes();
		if(springRequestAttributes != null) {
			return getSpringRequest(springRequestAttributes);
		}
		// 兼容 jetty
		Object jettyHttpChannel = getJettyHttpChannel();
		if(jettyHttpChannel != null) {
			return getJettyRequest(jettyHttpChannel);
		}
		return null;
	}
	
	/**
	 * 获取当前 servlet 请求
	 * @param request
	 * @return
	 */
	public static HttpServletRequest getRequest(HttpServletRequest request) {
		return request != null ? request : getRequest();
	}
	
	/**
	 * 获取当前 servlet 响应
	 * @return
	 */
	public static HttpServletResponse getResponse() {
		Supplier<HttpServletResponse> supplier = getResponseSupplier();
		if(supplier != null) {
			return supplier.get();
		}
		// 兼容 spring
		Object springRequestAttributes = getSpringRequestAttributes();
		if(springRequestAttributes != null) {
			return getSpringResponse(springRequestAttributes);
		}
		// 兼容 jetty
		Object jettyHttpChannel = getJettyHttpChannel();
		if(jettyHttpChannel != null) {
			return getJettyResponse(jettyHttpChannel);
		}
		return null;
	}
	
	/**
	 * 获取当前 servlet 响应
	 * @param response
	 * @return
	 */
	public static HttpServletResponse getResponse(HttpServletResponse response) {
		return response != null ? response : getResponse();
	}
	
	/**
	 * 获取 Spring RequestAttributes
	 * @return
	 */
	public static Object getSpringRequestAttributes() {
		Class<?> requestContextHolderClass = ReflectUtils.getClassByName("org.springframework.web.context.request.RequestContextHolder");
		if(requestContextHolderClass == null) {
			return null;
		}
		try {
			return ReflectUtils.invokeMethod(requestContextHolderClass, "getRequestAttributes", null, null, null);
		} catch (Exception e) {
			LogUtils.debug(ServletUtils.class, e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 获取spring 当前请求对象
	 * @param requestAttributes
	 * @return
	 */
	private static HttpServletRequest getSpringRequest(Object springRequestAttributes) {
		if(springRequestAttributes == null) {
			return null;
		}
		try {
			return (HttpServletRequest)ReflectUtils.invokeMethod(springRequestAttributes.getClass(), "getRequest", null, springRequestAttributes, null);
		} catch (Exception e) {
			LogUtils.error(ServletUtils.class, e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 获取 spring 当前响应对象
	 * @param springRequestAttributes
	 * @return
	 */
	private static HttpServletResponse getSpringResponse(Object springRequestAttributes) {
		if(springRequestAttributes == null) {
			return null;
		}
		try {
			return (HttpServletResponse)ReflectUtils.invokeMethod(springRequestAttributes.getClass(), "getResponse", null, springRequestAttributes, null);
		} catch (Exception e) {
			LogUtils.error(ServletUtils.class, e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 获取jetty 当前 HttpChannel
	 * @return
	 */
	public static Object getJettyHttpChannel() {
		Class<?> httpConnectionClass = ReflectUtils.getClassByName("org.eclipse.jetty.server.HttpConnection");
		if(httpConnectionClass == null) {
			return null;
		}
		try {
			Object httpConnection = ReflectUtils.invokeMethod(httpConnectionClass, "getCurrentConnection", null, null, null);
			if(httpConnection == null) {
				return null;
			}
			return ReflectUtils.invokeMethod(httpConnectionClass, "getHttpChannel", null, httpConnection, null);
		} catch (Exception e) {
			LogUtils.debug(ServletUtils.class, e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 获取 jetty 当前请求对象
	 * @param httpChannel
	 * @return
	 */
	private static HttpServletRequest getJettyRequest(Object jettyHttpChannel) {
		if(jettyHttpChannel == null) {
			return null;
		}
		try {
			return (HttpServletRequest)ReflectUtils.invokeMethod(jettyHttpChannel.getClass(), "getRequest", null, jettyHttpChannel, null);
		} catch (Exception e) {
			LogUtils.debug(ServletUtils.class, e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 获取 jetty 当前响应对象
	 * @param httpChannel
	 * @return
	 */
	private static HttpServletResponse getJettyResponse(Object jettyHttpChannel) {
		if(jettyHttpChannel == null) {
			return null;
		}
		try {
			return (HttpServletResponse)ReflectUtils.invokeMethod(jettyHttpChannel.getClass(), "getResponse", null, jettyHttpChannel, null);
		} catch (Exception e) {
			LogUtils.debug(ServletUtils.class, e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 获取 ServletContext
	 * @param request
	 * @return
	 */
	public static ServletContext getRequestServletContext(HttpServletRequest request) {
		if(request == null && (request = getRequest()) == null) {
			return null;
		}
		return request.getServletContext();
	}
	
	/**
	 * 获取当前请求会话
	 * @param request
	 * @return
	 */
	public static HttpSession getSession(HttpServletRequest request) {
		if(request == null && (request = getRequest()) == null) {
			return null;
		}
		return request.getSession();
	}
	
	/**
	 * 获取请求函数
	 * @param request
	 * @return
	 */
	public static String getRequestMethod(HttpServletRequest request) {
		if(request == null && (request = getRequest()) == null) {
			return null;
		}
		return request.getMethod();
	}
	
	/**
	 * 请求是否为GET函数
	 * @param request
	 * @return
	 */
	public static boolean isRequestGetMethod(HttpServletRequest request) {
		String method = getRequestMethod(request);
		return HttpConstants.HTTP_REQUEST_METHOD_GET.equalsIgnoreCase(method);
	}
	
	/**
	 * 请求是否为POST函数
	 * @param request
	 * @return
	 */
	public static boolean isRequestPostMethod(HttpServletRequest request) {
		String method = getRequestMethod(request);
		return HttpConstants.HTTP_REQUEST_METHOD_POST.equalsIgnoreCase(method);
	}
	
	/**
	 * 请求是否为OPTIONS函数
	 * @param request
	 * @return
	 */
	public static boolean isRequestOptionsMethod(HttpServletRequest request) {
		String method = getRequestMethod(request);
		return HttpConstants.HTTP_REQUEST_METHOD_OPTIONS.equalsIgnoreCase(method);
	}
	
	/**
	 * 请求是否为ajax
	 * @param request
	 * @return
	 */
	public static boolean isRequestAjax(HttpServletRequest request) {
		String xRequestedWith = getRequestHeader(request, HttpConstants.HTTP_HEADER_X_REQUESTED_WITH, true);
		return StringUtils.isNotEmpty(xRequestedWith) && "XMLHttpRequest".equalsIgnoreCase(xRequestedWith);
	}
	
	/**
	 * 是否为Multipart类型表单，此类型表单用于文件上传
	 * @param request
	 * @return
	 */
	public static boolean isRequestMultipart(HttpServletRequest request) {
		if(!isRequestPostMethod(request)) {
			return false;
		}
		String contentType = request.getContentType();
		if(StringUtils.isEmpty(contentType)) {
			return false;
		}
		return contentType.toLowerCase().startsWith("multipart/");
	}
	
	/**
	 * 判断请求路径是否匹配
	 * @param request
	 * @param pattern
	 * @return
	 */
	public static boolean isRequestPathMatch(HttpServletRequest request, String pattern) {
		String requestURI = request.getServletPath();
		return PATH_MATCHER.match(pattern, requestURI);
	}
	
	/**
	 * 获取 header map
	 * @param request
	 * @return
	 */
	public static Map<String, String> getRequestHeaderMap(HttpServletRequest request) {
		if(request == null && (request = getRequest()) == null) {
			return new HashMap<>();
		}
		Map<String, String> headers = new LinkedHashMap<>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			headers.put(headerName, StringUtils.trim(request.getHeader(headerName)));
		}
		return headers;
	}
	
	
	/**
	 * 获取请求 header
	 * @param request
	 * @param name header名称
	 * @return
	 */
	public static String getRequestHeader(HttpServletRequest request, String name) {
		return getRequestHeader(request, name, false);
	}
	
	/**
	 * 获取请求 header
	 * @param request
	 * @param headerName header名称
	 * @param ignoreCaseName 是否忽略header名称大小写
	 * @return
	 */
	public static String getRequestHeader(HttpServletRequest request, String headerName, boolean ignoreCaseName) {
		if(request == null && (request = getRequest()) == null) {
			return null;
		}
		String header = null;
		if(!ignoreCaseName) {
			header = request.getHeader(headerName);
		} else {
			Enumeration<String> names = request.getHeaderNames();
			String name = null;
			while (names.hasMoreElements()) {
				name = names.nextElement();
				if (name != null && name.equalsIgnoreCase(headerName)) {
					header = request.getHeader(name);
					break;
				}
			}
		}
		if(header == null) {
			return header;
		}
		// 去除首尾空白符
		header = StringUtils.trim(header);
		return header;
	}
	
	/**
	 * 设置响应的Header
	 * @param response
	 * @param name header名称
	 * @param value 值，可以是String，Date， int
	 */
	public static void setResponseHeader(HttpServletResponse response, String name, Object value) {
		if(response == null && (response = getResponse()) == null) {
			return;
		}
		if(value == null) {
			response.setHeader(name, null);
			return;
		}
		if (value instanceof String) {
			response.setHeader(name, (String) value);
		} else if (Date.class.isAssignableFrom(value.getClass())) {
			response.setDateHeader(name, ((Date) value).getTime());
		} else if (value instanceof Integer || "int".equals(value.getClass().getSimpleName().toLowerCase())) {
			response.setIntHeader(name, (Integer) value);
		} else {
			response.setHeader(name, value.toString());
		}
	}
	
	/**
	 * 获取 param map
	 * @param request
	 * @return
	 */
	public static Map<String, String> getRequestParameterMap(HttpServletRequest request) {
		if(request == null && (request = getRequest()) == null) {
			return new HashMap<>();
		}
		Map<String, String> params = new LinkedHashMap<>();
		Enumeration<String> paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			params.put(paramName, StringUtils.trim(request.getParameter(paramName)));
		}
		return params;
	}
	
	/**
	 * 获取请求参数
	 * @param request
	 * @param name 参数名称
	 * @return
	 */
	public static String getRequestParameter(HttpServletRequest request, String name) {
		return getRequestParameter(request, name, CharsetConstants.UTF_8);
	}
	
	/**
	 * 获取请求参数
	 * @param request
	 * @param name 参数名称
	 * @param charset 字符集
	 * @return
	 */
	public static String getRequestParameter(HttpServletRequest request, String name, String charset) {
		if(request == null && (request = getRequest()) == null) {
			return null;
		}
		String parameter = request.getParameter(name);
		if(parameter == null) {
			return parameter;
		}
		//字符集不匹配则自动转换
		if(charset != null && !charset.equalsIgnoreCase(request.getCharacterEncoding())) {
			String characterEncoding = request.getCharacterEncoding();
			try {
				parameter = new String(parameter.getBytes(characterEncoding), charset);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//清除首尾空白
		parameter = StringUtils.trim(parameter);
		return parameter;
	}
	
	/**
	 * 获取请求整型参数
	 * @param request
	 * @param name 参数名
	 * @return
	 */
	public static Integer getRequestParameterInteger(HttpServletRequest request, String name) {
		return getRequestParameterInteger(request, name, null);
	}
	
	/**
	 * 获取请求整型参数
	 * @param request
	 * @param name 参数名
	 * @param defValue 默认值
	 * @return
	 */
	public static Integer getRequestParameterInteger(HttpServletRequest request, String name, Integer defValue) {
		String parameter = getRequestParameter(request, name, null);
		if(NumberUtils.isInteger(parameter)) {
			return NumberUtils.parseInt(parameter);
		}
		return defValue;
	}
	
	/**
	 * 获取请求长整型参数
	 * @param request
	 * @param name 参数名
	 * @return
	 */
	public static Long getRequestParameterLong(HttpServletRequest request, String name) {
		return getRequestParameterLong(request, name, null);
	}
	
	/**
	 * 获取请求长整型参数
	 * @param request
	 * @param name 参数名
	 * @param defValue 默认值
	 * @return
	 */
	public static Long getRequestParameterLong(HttpServletRequest request, String name, Long defValue) {
		String parameter = getRequestParameter(request, name, null);
		if(NumberUtils.isLong(parameter)) {
			return NumberUtils.parseLong(parameter);
		}
		return defValue;
	}
	
	/**
	 * 获取请求布尔值参数
	 * @param request
	 * @param name 参数名
	 * @return
	 */
	public static Boolean getRequestParameterBoolean(HttpServletRequest request, String name) {
		return getRequestParameterBoolean(request, name, null);
	}
	
	/**
	 * 获取请求布尔值参数
	 * @param request
	 * @param name 参数名
	 * @param defValue 默认值
	 * @return
	 */
	public static Boolean getRequestParameterBoolean(HttpServletRequest request, String name, Boolean defValue) {
		String parameter = getRequestParameter(request, name, null);
		if(StringUtils.isNotEmpty(parameter)) {
			return BooleanUtils.toBoolean(parameter);
		}
		return defValue;
	}
	
	/**
	 * 获取 cookie 集合
	 * @param request
	 * @return
	 */
	public static Map<String, Cookie> getRequestCookieMap(HttpServletRequest request) {
		if(request == null && (request = getRequest()) == null) {
			return new LinkedHashMap<>();
		}
		Map<String, Cookie> cookieMap = new LinkedHashMap<>();
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return cookieMap;
		}
		for (Cookie cookie : cookies) {
			cookieMap.put(cookie.getName(), cookie);
		}
		return cookieMap;
	}
	
	/**
	 * 获取 cookie 值
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getRequestCookieValue(HttpServletRequest request, String name) {
		Cookie cookie = getRequestCookie(request, name);
		if(cookie == null) {
			return null;
		}
		String value = cookie.getValue();
		if(value != null) {
			value = value.trim();
		}
		return value;
	}
	
	/**
	 * 获取 cookie
	 * @param request
	 * @param name cookie 名称(忽略大小写)
	 * @return
	 */
	public static Cookie getRequestCookie(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = getRequestCookieMap(request);
		if(cookieMap.size() == 0) {
			return null;
		}
		for(Entry<String, Cookie> kv : cookieMap.entrySet()) {
			if(kv.getKey().equalsIgnoreCase(name)) {
				return kv.getValue();
			}
		}
		return null;
	}
	
	/**
	 * 设定返回给客户端的Cookie
	 * @param response
	 * @param name cookie名称
	 * @param value cookie值
	 * @param maxAgeInSeconds -1: 关闭浏览器清除Cookie. 0: 立即清除Cookie. >0 : Cookie存在的秒数.
	 * @return 
	 */
	public static Cookie setRequestCookie(HttpServletResponse response, String name, String value, int maxAgeInSeconds) {
		return setRequestCookie(response, name, value, null, "/", maxAgeInSeconds, false);
	}
	
	/**
	 * 设定返回给客户端的Cookie
	 * @param response
	 * @param name cookie名称
	 * @param value cookie值
	 * @param domain 域名
	 * @param maxAgeInSeconds -1: 关闭浏览器清除Cookie. 0: 立即清除Cookie. >0 : Cookie存在的秒数.
	 * @return 
	 */
	public static Cookie setRequestCookie(HttpServletResponse response, String name, String value, String domain, int maxAgeInSeconds) {
		return setRequestCookie(response, name, value, domain, "/", maxAgeInSeconds, false);
	}
	
	/**
	 * 设定返回给客户端的Cookie
	 * @param response
	 * @param name cookie名称
	 * @param value cookie值
	 * @param domain 域名
	 * @param path 路径
	 * @param maxAgeInSeconds -1: 关闭浏览器清除Cookie. 0: 立即清除Cookie. >0 : Cookie存在的秒数.
	 * @param isHttpOnly 
	 * @return
	 */
	public static Cookie setRequestCookie(HttpServletResponse response, String name, String value, String domain, String path, int maxAgeInSeconds, boolean isHttpOnly) {
		if(response == null && (response = getResponse()) == null) {
			return null;
		}
		Cookie cookie = new Cookie(name, value);
		if (domain != null) {
			cookie.setDomain(domain);
		}
		if(path != null) {
			cookie.setPath(path);
		}
		cookie.setMaxAge(maxAgeInSeconds);
		cookie.setHttpOnly(isHttpOnly);
		response.addCookie(cookie);
		return cookie;
	}
	
	/**
	 * 获取请求 token 值
	 * @param request
	 * @param tokenName token名称
	 * @param allowUrlParam 是否允许url参数
	 * @param allowHeader 是否允许header参数
	 * @param allowCookie 是否允许cookie参数
	 * @return
	 */
	public static String getRequestTokenValue(HttpServletRequest request, String tokenName, boolean allowUrlParam, boolean allowHeader, boolean allowCookie) {
		if(request == null && (request = getRequest()) == null) {
			return null;
		}
		String tokenValue = null;
		// parameter
		if(allowUrlParam) {
			tokenValue = getRequestParameter(request, tokenName, null);
			if(!StringUtils.isEmpty(tokenValue)) {
				return tokenValue;
			}
		}
		// header
		if(allowHeader) {
			tokenValue = getRequestHeader(request, tokenName);
			if(!StringUtils.isEmpty(tokenValue)) {
				return tokenValue;
			}
		}
		// cookie
		if(allowCookie) {
			tokenValue = getRequestCookieValue(request, tokenName);
			if(!StringUtils.isEmpty(tokenValue)) {
				return tokenValue;
			}
		}
		return tokenValue;
	}
	
	/**
	 * 设置请求token值
	 * @param response
	 * @param tokenName
	 * @param allowHeader
	 * @param allowCookie
	 */
	public static void setRequestTokenValue(HttpServletResponse response, String tokenName, String tokenValue, int tokenTimeout, boolean allowHeader, boolean allowCookie) {
		if(response == null && (response = getResponse()) == null) {
			return;
		}
		if(allowCookie) {
			setRequestCookie(response, tokenName, tokenValue, null, !StringUtils.isEmpty(tokenValue) ? tokenTimeout : 0);
		}
		if(allowHeader) {
			setResponseHeader(response, tokenName, tokenValue);
		}
	}
	
	/**
	 * 获取请求根路径
	 * @param request
	 * @return
	 */
	public static String getRequestBaseURL(ServletRequest request){
		if(request == null && (request = getRequest()) == null) {
			return StringConstants.STRING_EMPTY;
		}
		StringBuilder str = new StringBuilder();
		str.append(request.getScheme());
		str.append("://");
		str.append(request.getServerName());
		if(request.getScheme().equalsIgnoreCase(UrlConstants.URL_PROTOCOL_HTTP) && request.getServerPort() == UrlConstants.URL_PORT_HTTP) {
			str.append("");
		}else if(request.getScheme().equalsIgnoreCase(UrlConstants.URL_PROTOCOL_HTTPS) && request.getServerPort() == UrlConstants.URL_PORT_HTTPS) {
			str.append("");
		}else {
			str.append(":");
			str.append(request.getServerPort());
		}
		str.append(request.getServletContext().getContextPath());
		return str.toString(); 
	}
	
	/**
	 * 获取请求 URL
	 * @param request
	 * @return
	 */
	public static String getRequestURL(HttpServletRequest request) {
		return getRequestURL(request, false);
	}
	
	/**
	 * 获取请求 URL
	 * @param request
	 * @param includeQueryString 是否包含查询参数
	 * @return
	 */
	public static String getRequestURL(HttpServletRequest request, boolean includeQueryString) {
		if(request == null && (request = getRequest()) == null) {
			return StringConstants.STRING_EMPTY;
		}
		// 获取当前请求的 url
		StringBuffer requestUrl = request.getRequestURL();
		if(includeQueryString) {
			String queryString = request.getQueryString();
			if(StringUtils.isNotEmpty(queryString) && !queryString.contains("?")) {
				requestUrl.append("?");
				requestUrl.append(queryString);
			}
		}
		return requestUrl.toString();
	}
	
	/**
	 * 获取请求 servlet 路径
	 * @param request
	 * @return
	 */
	public static String getRequestServletPath(HttpServletRequest request) {
		if(request == null && (request = getRequest()) == null) {
			return StringConstants.STRING_EMPTY;
		}
		return request.getServletPath();
	}
	
	/**
	 * 获取请求主体文本
	 * @param request
	 * @param charset
	 * @return
	 */
	public static String getRequestBody(ServletRequest request, String charset) {
		byte[] bytes = getRequestBody(request);
		return bytes != null ? StringUtils.toString(bytes, charset) : null;
	}
	
	/**
	 * 获取请求主体二进制数据
	 * @param request
	 * @return
	 */
	public static byte[] getRequestBody(ServletRequest request) {
		BufferedReader reader = getRequestReader(request);
		try {
			return IoUtils.toByteArray(reader);
		} catch (Exception e) {
			throw new UtilException(e);
		} finally {
			IoUtils.close(reader);
		}
	}
	
	/**
	 * 获取请求 BufferedReader 对象 
	 * @param request
	 * @return
	 */
	public static BufferedReader getRequestReader(ServletRequest request) {
		if(request == null && (request = getRequest()) == null) {
			return null;
		}
		try {
			return request.getReader();
		} catch (Exception e) {
			throw new UtilException(e);
		}
	}
	
	/**
	 * 获取响应 PrintWriter 对象
	 * @param response
	 * @return
	 */
	public static PrintWriter getResponseWriter(ServletResponse response) {
		if(response == null && (response = getResponse()) == null) {
			return null;
		}
		try {
			return response.getWriter();
		} catch (Exception e) {
			throw new UtilException(e);
		}
	}
	
	/**
	 * 获取请求来源地址
	 * @param request
	 * @return
	 */
	public static String getRequestOrgin(HttpServletRequest request) {
		return getRequestHeader(request, HttpConstants.HTTP_HEADER_ORIGIN, true);
	}
	
	/**
	 * 获取请求引用地址
	 * @param request
	 * @return
	 */
	public static String getRequestReferer(HttpServletRequest request) {
		return getRequestHeader(request, HttpConstants.HTTP_HEADER_REFERER, true);
	}
	
	/**
	 * 获取请求客户端UA信息
	 * @param request
	 * @return
	 */
	public static String getRequestUserAgent(HttpServletRequest request) {
		return getRequestHeader(request, HttpConstants.HTTP_HEADER_USER_AGENT, true);
	}
	
	/**
	 * 获取请求客户端IP
	 * @param request
	 * @param 其他自定义头文件 可空
	 * @return
	 */
	public static String getRequestClientIP(HttpServletRequest request, String... otherHeaderNames) {
		if(request == null && (request = getRequest()) == null) {
			return StringConstants.STRING_EMPTY;
		}
		String[] headers = { "X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR" };
		if (ArrayUtils.isNotEmpty(otherHeaderNames)) {
			headers = ArrayUtils.append(headers, otherHeaderNames);
		}
		String ip;
		for (String header : headers) {
			ip = request.getHeader(header);
			if (!IpUtils.isUnknown(ip)) {
				return IpUtils.getMultistageReverseProxyIp(ip);
			}
		}
		ip = request.getRemoteAddr();
		return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : IpUtils.getMultistageReverseProxyIp(ip);
	}
	
	/**
	 * 获取请求客户端支持的语言
	 * @param request
	 * @return
	 */
	public static String getRequestAcceptLanguage(HttpServletRequest request) {
		return getRequestHeader(request, HttpConstants.HTTP_HEADER_ACCEPT_LANGUAGE, true);
	}
	
	/**
	 * 从header中获取请求认证令牌
	 * @param request
	 * @param prefix 前缀(如Bearer )
	 * @return
	 */
	public static String getRequestAuthorization(HttpServletRequest request, String prefix) {
		String headerValue = getRequestHeader(request, HttpConstants.HTTP_HEADER_AUTHORIZATION, true);
		if(headerValue != null && prefix != null && StringUtils.startsWith(headerValue, prefix)) {
			headerValue = headerValue.replaceFirst(prefix, "");
		}
		return headerValue;
	}
	
	/**
	 * 发送重定向响应
	 * @param response
	 * @param targetUrl 要重定向的目标url
	 * @return
	 */
	public static boolean sendResponseRedirect(HttpServletResponse response, String targetUrl) {
		if(response == null && (response = getResponse()) == null) {
			return false;
		}
		try {
			response.sendRedirect(response.encodeRedirectURL(targetUrl));
			return true;
		} catch (Exception e) {
			LogUtils.error(ServletUtils.class, e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * 输出响应html文本
	 * @param response 响应对象
	 * @param text 输出文本
	 * @return
	 */
	public static boolean writeResponseHtml(HttpServletResponse response, String text) {
		return writeResponseContent(response, HttpConstants.HTTP_CONTENTTYPE_TEXT_HTML_UTF8, text);
	}
	
	/**
	 * 输出响应json文本
	 * @param response
	 * @param text
	 * @return
	 */
	public static boolean writeResponseJson(HttpServletResponse response, String text) {
		return writeResponseContent(response, HttpConstants.HTTP_CONTENTTYPE_APPLICATION_JSON_UTF8, text);
	}
	
	/**
	 * 输出响应js
	 * @param response
	 * @param text
	 * @return
	 */
	public static boolean writeResponseJs(HttpServletResponse response, String text) {
		return writeResponseContent(response, HttpConstants.HTTP_CONTENTTYPE_APPLICATION_JAVASCRIPT, text);
	}
	
	/**
	 * 输出响应js
	 * @param response
	 * @param text
	 * @return
	 */
	public static boolean writeResponseCss(HttpServletResponse response, String text) {
		return writeResponseContent(response, HttpConstants.HTTP_CONTENTTYPE_TEXT_CSS, text);
	}
	
	/**
	 * 输出响应内容
	 * @param response 响应对象
	 * @param contentType 内容类型
	 * @param content 输出内容
	 * @return
	 */
	public static boolean writeResponseContent(HttpServletResponse response, String contentType, String content) {
		if(response == null && (response = getResponse()) == null) {
			return false;
		}
		PrintWriter writer = null;
		try {
			// 响应字符集
			response.setCharacterEncoding(DEFAULT_CHARSET);
			// 内容类型
			if(!StringUtils.isEmpty(contentType)) {
				response.setContentType(contentType);
			}
			// 内容
			if(content == null) {
				content = StringConstants.STRING_EMPTY;
			}
			writer = response.getWriter();
			writer.write(content);
			writer.flush();
			return true;
		} catch (Exception e) {
			LogUtils.error(ServletUtils.class, e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * 输出响应文件
	 * @param response 响应对象
	 * @param inputStream 流对象
	 * @param bufferSize 缓冲区大小
	 * @return
	 */
	public static long writeResponseFile(HttpServletResponse response, File file, String fileName) {
		return writeResponseFile(response, HttpConstants.HTTP_CONTENTTYPE_APPLICATION_OCTET_STREAM, file, fileName);
	}
	
	/**
	 * 输出响应文件
	 * @param response 响应对象
	 * @param contentType 内容类型
	 * @param inputStream 流对象
	 * @param bufferSize 缓冲区大小
	 * @return
	 */
	public static long writeResponseFile(HttpServletResponse response, String contentType, File file, String fileName) {
		if(response == null && (response = getResponse()) == null) {
			return -1;
		}
		InputStream inputStream = null;
		try {
			// 文件长度
			Long fileLength = FileUtils.getFileLength(file);
			if(fileLength == null) {
				return -1;
			}
			response.setHeader(HttpConstants.HTTP_HEADER_CONTENT_LENGTH, String.valueOf(fileLength));
			// 文件名
			if(StringUtils.isEmpty(fileName)){
				fileName = FileUtils.getFileName(file.getAbsolutePath());
			}
			response.setHeader(HttpConstants.HTTP_HEADER_CONTENT_DISPOSITION, "attachment;filename=" + UrlUtils.encode(fileName, DEFAULT_CHARSET));
			// 准备输入流
			inputStream = new BufferedInputStream(new FileInputStream(file));
			// 输出二进制流
			return writeResponseStream(response, contentType, inputStream, DEFAULT_BUFFER_SIZE);
		} catch (Exception e) {
			LogUtils.error(ServletUtils.class, e.getMessage(), e);
			return -1;
		} finally {
			IoUtils.close(inputStream);
		}
	}
	
	/**
	 * 输出响应二进制流
	 * @param response 响应对象
	 * @param contentType 内容类型
	 * @param inputStream 流对象
	 * @param bufferSize 缓冲区大小
	 * @return
	 */
	public static long writeResponseStream(HttpServletResponse response, String contentType, InputStream inputStream, int bufferSize) {
		if(response == null && (response = getResponse()) == null) {
			return -1;
		}
		ServletOutputStream outputStream = null;
		long writeBytes = 0;
		try {
			// 响应字符集
			response.setCharacterEncoding(DEFAULT_CHARSET);
			// 内容类型
			if(!StringUtils.isEmpty(contentType)) {
				response.setContentType(contentType);
			}
			// 设置数据长度
			if(StringUtils.isEmpty(response.getHeader(HttpConstants.HTTP_HEADER_CONTENT_LENGTH))) {
				Long length = IoUtils.getLength(inputStream);
				if(length != null) {
					response.setHeader(HttpConstants.HTTP_HEADER_CONTENT_LENGTH, String.valueOf(length));
				}
			}
			// 缓冲区大小
			if(bufferSize < 1) {
				bufferSize = DEFAULT_BUFFER_SIZE;
			}
			outputStream = response.getOutputStream();
			writeBytes = IoUtils.copyLarge(inputStream, outputStream, bufferSize, null);
			outputStream.flush();
			return writeBytes;
		} catch (Exception e) {
			LogUtils.error(ServletUtils.class, e.getMessage(), e);
			return -1;
		}
	}
}
