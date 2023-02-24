package io.github.openguava.jvtool.lang.util;

import io.github.openguava.jvtool.lang.codec.base64.Base64Decoder;
import io.github.openguava.jvtool.lang.codec.base64.Base64Encoder;
import io.github.openguava.jvtool.lang.codec.hex.HexDecoder;
import io.github.openguava.jvtool.lang.codec.hex.HexEncoder;
import io.github.openguava.jvtool.lang.codec.unicode.UnicodeDecoder;
import io.github.openguava.jvtool.lang.codec.unicode.UnicodeEncoder;
import io.github.openguava.jvtool.lang.constant.CharsetConstants;

/**
 * 编码器工具类
 * @author openguava
 *
 */
public class CodecUtils {

	/**
	 * base64编码
	 * @param source
	 * @return
	 */
	public static String encodeBase64(String source) {
		return Base64Encoder.encode(source);
	}
	
	/**
	 * base64编码吗
	 * @param source
	 * @return
	 */
	public static String encodeBase64(byte[] source) {
		return Base64Encoder.encode(source);
	}
	
	/**
	 * base64解码
	 * @param source
	 * @return
	 */
	public static byte[] encodeBase64(CharSequence source) {
		return Base64Decoder.decode(source);
	}
	
	/**
	 * base64解码
	 * @param source
	 * @return
	 */
	public static byte[] decodeBase64(byte[] source) {
		return Base64Decoder.decode(source);
	}
	
	/**
	 * 十六进制编码
	 * @param source
	 * @return
	 */
	public static char[] encodeHex(byte[] source) {
		return HexEncoder.encode(source);
	}
	
	/**
	 * 十六进制编码
	 * @param source
	 * @return
	 */
	public static String encodeHexStr(byte[] source) {
		return HexEncoder.encodeStr(source);
	}
	
	/**
	 * 十六进制编码
	 * @param source
	 * @return
	 */
	public static String encodeHexStr(String source) {
		return HexEncoder.encodeStr(source.getBytes(CharsetConstants.CHARSET_DEFAULT));
	}
	
	/**
	 * 十六进制解码
	 * @param hexData
	 * @return
	 */
	public static byte[] decodeHex(CharSequence hexData) {
		return HexDecoder.decode(hexData);
	}
	
	/**
	 * Unicode 编码
	 * @param str
	 * @return
	 */
	public static String encodeUnicode(String str) {
		return UnicodeEncoder.encode(str);
	}
	
	/**
	 * Unicode 解码
	 * @param unicode
	 * @return
	 */
	public static String decodeUnicode(String unicode) {
		return UnicodeDecoder.decode(unicode);
	}
	
	public static void main(String[] args) {
		String str = "a";
		System.out.println(encodeUnicode(str));
	}
}
