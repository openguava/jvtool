package io.github.openguava.jvtool.lang.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.URLStreamHandler;

import io.github.openguava.jvtool.lang.constant.CharsetConstants;
import io.github.openguava.jvtool.lang.constant.StringConstants;
import io.github.openguava.jvtool.lang.constant.UrlConstants;
import io.github.openguava.jvtool.lang.exception.UtilException;

/**
 * url 工具类
 * @author openguava
 *
 */
public class UrlUtils {

	/**
	 * 获取URL对象
	 * @param url
	 * @return
	 */
	public static URL getURL(String url) {
		return getURL(url, null);
	}
	
	/**
	 * 获取URL对象
	 * @param url
	 * @return
	 */
	public static URL getURL(String url, URLStreamHandler handler) {
		if(url == null) {
			return null;
		}
		// 兼容spring的classpath路径
		if (url.startsWith(UrlConstants.URL_PROTOCOL_CLASSPATH_PREFIX)) {
			url = url.substring(UrlConstants.URL_PROTOCOL_CLASSPATH_PREFIX.length());
			return ReflectUtils.getClassLoader().getResource(url);
		}
		try {
			return new URL(null, url, handler);
		} catch (MalformedURLException e) {
			// 尝试文件路径
			try {
				return new File(url).toURI().toURL();
			} catch (MalformedURLException ex2) {
				throw new UtilException(e);
			}
		}
	}
	
	/**
	 * 获取 url 协议
	 * @param url
	 * @return
	 */
	public static String getProtocol(String url) {
		return getURL(url).getProtocol();
	}
	
	/**
	 * 获取 url 主机名
	 * @param url
	 * @return
	 */
	public static String getHost(String url) {
		return getURL(url).getHost();
	}
	
	/**
	 * 获取 url 端口好
	 * @param url
	 * @return
	 */
	public static int getPort(String url) {
		URL u = getURL(url);
		int port = u.getPort();
		if(port == -1) {
			if(u.getProtocol().equalsIgnoreCase(UrlConstants.URL_PROTOCOL_HTTPS)) {
				port = UrlConstants.URL_PORT_HTTPS;
			} else if(u.getProtocol().equalsIgnoreCase(UrlConstants.URL_PROTOCOL_HTTP)) {
				port = UrlConstants.URL_PORT_HTTP;
			} else if(u.getProtocol().equalsIgnoreCase(UrlConstants.URL_PROTOCOL_FTP)) {
				port = UrlConstants.URL_PORT_FTP;
			} else if(u.getProtocol().equalsIgnoreCase(UrlConstants.URL_PROTOCOL_RTSP)) {
				port = UrlConstants.URL_PORT_RTSP;
			} else if(u.getProtocol().equalsIgnoreCase(UrlConstants.URL_PROTOCOL_RTMP)) {
				port = UrlConstants.URL_PORT_RTMP;
			}
		}
		return port; 
	}
	
	/**
	 * 获取 url 路径信息
	 * @param url
	 * @return
	 */
	public static String getPath(String url) {
		return getURL(url).getPath();
	}
	
	/**
	 * 获取 url 查询参数
	 * @param url
	 * @return
	 */
	public static String getQuery(String url) {
		return getURL(url).getQuery();
	}
	
	/**
	 * 获取 url 参数
	 * @param url
	 * @param name 参数名
	 * @return
	 */
	public static String getParameter(String url, String name) {
		String query = getQuery(url);
		if(query == null || query.length() == 0) {
			return null;
		}
		//补全&符号
		query = "&" + query;
		//beginIndex
		int beginIndex = query.toLowerCase().indexOf("&" + name + "=");
		if(beginIndex < 0) {
			return null;
		}
		beginIndex = beginIndex + name.length() + 2;
		//endIndex
		int endIndex = query.toLowerCase().indexOf("&", beginIndex);
		if(endIndex < 0) {
			endIndex = query.length();
		}
		//value
		return query.substring(beginIndex, endIndex);
	}
	
	/**
	 * URL 编码
	 * @param text 文本
	 * @return
	 */
	public static String encode(String text) {
		return encode(text, null);
	}
	
	/**
	 * URL 编码
	 * @param text 文本
	 * @param charset 字符集
	 * @return
	 */
	public static String encode(String text, String charset) {
		try {
			if(StringUtils.isEmpty(charset)) {
				return URLEncoder.encode(text, CharsetConstants.UTF_8);
			} else {
				return URLEncoder.encode(text, charset);
			}
		} catch (Exception e) {
			LogUtils.warn(UrlConstants.class, e.getMessage(), e);
			return URLEncoder.encode(text);
		}
	}
	
	/**
	 * URL 解码
	 * @param text 文本
	 * @return
	 */
	public static String decode(String text) {
		return decode(text, null);
	}
	
	/**
	 * URL 解码
	 * @param text 文本
	 * @param charset 字符集
	 * @return
	 */
	public static String decode(String text, String charset) {
		try {
			if(StringUtils.isEmpty(charset)) {
				return URLDecoder.decode(text, CharsetConstants.UTF_8);
			} else {
				return URLDecoder.decode(text, charset);
			}
		} catch (Exception e) {
			LogUtils.warn(UrlConstants.class, e.getMessage(), e);
			return URLDecoder.decode(text);
		}
	}
	
	/**
	 * 标准化URL字符串，包括：
	 *
	 * <ol>
	 *     <li>自动补齐“http://”头</li>
	 *     <li>去除开头的\或者/</li>
	 *     <li>替换\为/</li>
	 * </ol>
	 *
	 * @param url URL字符串
	 * @return 标准化后的URL字符串
	 */
	public static String normalize(String url) {
		return normalize(url, false);
	}

	/**
	 * 标准化URL字符串，包括：
	 *
	 * <ol>
	 *     <li>自动补齐“http://”头</li>
	 *     <li>去除开头的\或者/</li>
	 *     <li>替换\为/</li>
	 * </ol>
	 *
	 * @param url          URL字符串
	 * @param isEncodePath 是否对URL中path部分的中文和特殊字符做转义（不包括 http:, /和域名部分）
	 * @return 标准化后的URL字符串
	 */
	public static String normalize(String url, boolean isEncodePath) {
		return normalize(url, isEncodePath, false);
	}
	
	/**
	 * 标准化URL字符串，包括：
	 *
	 * <ol>
	 *     <li>自动补齐“http://”头</li>
	 *     <li>去除开头的\或者/</li>
	 *     <li>替换\为/</li>
	 *     <li>如果replaceSlash为true，则替换多个/为一个</li>
	 * </ol>
	 *
	 * @param url          URL字符串
	 * @param isEncodePath 是否对URL中path部分的中文和特殊字符做转义（不包括 http:, /和域名部分）
	 * @param replaceSlash 是否替换url body中的 //
	 * @return 标准化后的URL字符串
	 */
	public static String normalize(String url, boolean isEncodePath, boolean replaceSlash) {
		if (StringUtils.isBlank(url)) {
			return url;
		}
		final int sepIndex = url.indexOf("://");
		String protocol;
		String body;
		if (sepIndex > 0) {
			protocol = StringUtils.subPre(url, sepIndex + 3);
			body = StringUtils.subSuf(url, sepIndex + 3);
		} else {
			protocol = "http://";
			body = url;
		}

		final int paramsSepIndex = StringUtils.indexOf(body, '?');
		String params = null;
		if (paramsSepIndex > 0) {
			params = StringUtils.subSuf(body, paramsSepIndex);
			body = StringUtils.subPre(body, paramsSepIndex);
		}

		if (StringUtils.isNotEmpty(body)) {
			// 去除开头的\或者/
			//noinspection ConstantConditions
			body = body.replaceAll("^[\\\\/]+", StringConstants.STRING_EMPTY);
			// 替换\为/
			body = body.replace("\\", "/");
			if (replaceSlash) {
				//issue#I25MZL@Gitee，双斜杠在URL中是允许存在的，默认不做替换
				body = body.replaceAll("//+", "/");
			}
		}

		final int pathSepIndex = StringUtils.indexOf(body, '/');
		String domain = body;
		String path = null;
		if (pathSepIndex > 0) {
			domain = StringUtils.subPre(body, pathSepIndex);
			path = StringUtils.subSuf(body, pathSepIndex);
		}
		if (isEncodePath) {
			path = encode(path);
		}
		return protocol + domain + StringUtils.nullToEmpty(path) + StringUtils.nullToEmpty(params);
	}
	
	/**
	 * 在url上拼接上kv参数并返回 
	 * @param url url
	 * @param key 参数名称
	 * @param value 参数值 
	 * @return 拼接后的url字符串 
	 */
	public static String joinParameter(String url, String key, Object value) {
		String strValue = StringUtils.toString(value, CharsetConstants.CHARSET_UTF_8);
		// 如果参数为空, 直接返回 
		if(StringUtils.isEmpty(url) || StringUtils.isEmpty(key) || StringUtils.isEmpty(strValue)) {
			return url;
		}
		return joinParameter(url, key + "=" + strValue);
	}
	
	/**
	 * 在url上拼接上kv参数并返回 
	 * @param url url
	 * @param parameStr 参数, 例如 id=1001
	 * @return 拼接后的url字符串 
	 */
	public static String joinParameter(String url, String parameStr) {
		// 如果参数为空, 直接返回 
		if(parameStr == null || parameStr.length() == 0) {
			return url;
		}
		if(url == null) {
			url = "";
		}
		int index = url.lastIndexOf('?');
		// ? 不存在
		if(index == -1) {
			return url + '?' + parameStr;
		}
		// ? 是最后一位
		if(index == url.length() - 1) {
			return url + parameStr;
		}
		// ? 是其中一位
		if(index > -1 && index < url.length() - 1) {
			String separatorChar = "&";
			// 如果最后一位是 不是&, 且 parameStr 第一位不是 &, 就增送一个 &
			if(url.lastIndexOf(separatorChar) != url.length() - 1 && parameStr.indexOf(separatorChar) != 0) {
				return url + separatorChar + parameStr;
			} else {
				return url + parameStr;
			}
		}
		// 正常情况下, 代码不可能执行到此 
		return url;
	}
	
	/**
	 * 是否 http/https
	 * @param url
	 * @return
	 */
	public static boolean isHttp(String url) {
		return StringUtils.startsWithAny(url, UrlConstants.URL_PREFIX_HTTP, UrlConstants.URL_PREFIX_HTTPS);
	}
	
	public static void main(String[] args) {
		
	}
}
