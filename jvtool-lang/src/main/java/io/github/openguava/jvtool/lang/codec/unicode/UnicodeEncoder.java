package io.github.openguava.jvtool.lang.codec.unicode;

import io.github.openguava.jvtool.lang.codec.hex.HexEncoder;
import io.github.openguava.jvtool.lang.util.CharUtils;

/**
 * Unicode 编码
 * @author openguava
 *
 */
public class UnicodeEncoder {

	/**
	 * 字符串编码为Unicode形式
	 * @param str
	 * @return
	 */
	public static String encode(String str) {
		return encode(str, true);
	}
	
	/**
	 * 字符串编码为Unicode形式
	 * 
	 * @param str 被编码的字符串
	 * @param isSkipAscii 是否跳过ASCII字符（只跳过可见字符）
	 * @return Unicode字符串
	 */
	public static String encode(String str, boolean isSkipAscii) {
		if (str == null || str.length() == 0) {
			return str;
		}
		final int len = str.length();
		final StringBuilder unicode = new StringBuilder(str.length() * 6);
		char c;
		for (int i = 0; i < len; i++) {
			c = str.charAt(i);
			if(isSkipAscii && CharUtils.isAsciiPrintable(c) ) {
				unicode.append(c);
			}else {
				unicode.append(HexEncoder.toUnicodeHex(c));
			} 
		}
		return unicode.toString();
	}
}
