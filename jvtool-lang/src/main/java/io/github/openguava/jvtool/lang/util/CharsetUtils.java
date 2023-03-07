package io.github.openguava.jvtool.lang.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

/**
 * 字符集 工具类
 *
 * @author openguava
 */
public class CharsetUtils {
	
	protected CharsetUtils() {
		
	}
	
	/**
	 * 转换为Charset对象
	 * @param charset 字符集
	 * @return
	 */
	public static Charset charset(final Charset charset) {
        return charset == null ? Charset.defaultCharset() : charset;
    }
	
	/**
	 * 转换为Charset对象
	 *
	 * @param charsetName 字符集，为空则返回默认字符集
	 * @return Charset
	 * @throws UnsupportedCharsetException 编码不支持
	 */
	public static Charset charset(String charsetName) throws UnsupportedCharsetException {
		return StringUtils.isBlank(charsetName) ? Charset.defaultCharset() : Charset.forName(charsetName);
	}

	/**
	 * 解析字符串编码为Charset对象，解析失败返回系统默认编码
	 *
	 * @param charsetName 字符集，为空则返回默认字符集
	 * @return Charset
	 */
	public static Charset parse(String charsetName) {
		return parse(charsetName, Charset.defaultCharset());
	}

	/**
	 * 解析字符串编码为Charset对象，解析失败返回默认编码
	 *
	 * @param charsetName    字符集，为空则返回默认字符集
	 * @param defaultCharset 解析失败使用的默认编码
	 * @return Charset
	 */
	public static Charset parse(String charsetName, Charset defaultCharset) {
		if (StringUtils.isBlank(charsetName)) {
			return defaultCharset;
		}

		Charset result;
		try {
			result = Charset.forName(charsetName);
		} catch (UnsupportedCharsetException e) {
			result = defaultCharset;
		}

		return result;
	}

	/**
	 * 转换字符串的字符集编码
	 *
	 * @param source      字符串
	 * @param srcCharset  源字符集，默认ISO-8859-1
	 * @param destCharset 目标字符集，默认UTF-8
	 * @return 转换后的字符集
	 */
	public static String convert(String source, String srcCharset, String destCharset) {
		return convert(source, Charset.forName(srcCharset), Charset.forName(destCharset));
	}

	/**
	 * 转换字符串的字符集编码<br>
	 * 当以错误的编码读取为字符串时，打印字符串将出现乱码。<br>
	 * 此方法用于纠正因读取使用编码错误导致的乱码问题。<br>
	 * 例如，在Servlet请求中客户端用GBK编码了请求参数，我们使用UTF-8读取到的是乱码，此时，使用此方法即可还原原编码的内容
	 * <pre>
	 * 客户端 -》 GBK编码 -》 Servlet容器 -》 UTF-8解码 -》 乱码
	 * 乱码 -》 UTF-8编码 -》 GBK解码 -》 正确内容
	 * </pre>
	 *
	 * @param source      字符串
	 * @param srcCharset  源字符集，默认ISO-8859-1
	 * @param destCharset 目标字符集，默认UTF-8
	 * @return 转换后的字符集
	 */
	public static String convert(String source, Charset srcCharset, Charset destCharset) {
		if (null == srcCharset) {
			srcCharset = StandardCharsets.ISO_8859_1;
		}

		if (null == destCharset) {
			destCharset = StandardCharsets.UTF_8;
		}

		if (StringUtils.isBlank(source) || srcCharset.equals(destCharset)) {
			return source;
		}
		return new String(source.getBytes(srcCharset), destCharset);
	}

	/**
	 * 获取系统默认字符集编码
	 *
	 * @return 系统字符集编码
	 */
	public static String getDefaultCharsetName() {
		return getDefaultCharset().name();
	}

	/**
	 * 获取系统默认字符集编码
	 *
	 * @return 系统字符集编码
	 */
	public static Charset getDefaultCharset() {
		return Charset.defaultCharset();
	}
}
