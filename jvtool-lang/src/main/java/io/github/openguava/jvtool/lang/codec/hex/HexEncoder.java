package io.github.openguava.jvtool.lang.codec.hex;

/**
 * 十六进制编码
 * @author openguava
 *
 */
public class HexEncoder {

	/** 用于建立十六进制字符的输出的小写字符数组 */
	public static final char[] CHARS_HEX_DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	/** 用于建立十六进制字符的输出的大写字符数组 */
	public static final char[] CHARS_HEX_DIGITS_UPPER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	
	/**
	 * 将字节数组转换为十六进制字符串
	 *
	 * @param data byte[]
	 * @return 十六进制String
	 */
	public static String encodeStr(byte[] data) {
		return encodeStr(data, true);
	}
	
	/**
	 * 将字节数组转换为十六进制字符串
	 *
	 * @param data        byte[]
	 * @param toLowerCase {@code true} 传换成小写格式 ， {@code false} 传换成大写格式
	 * @return 十六进制String
	 */
	public static String encodeStr(byte[] data, boolean toLowerCase) {
		return new String(encode(data, toLowerCase));
	}
	
	/**
	 * 将字节数组转换为十六进制字符数组
	 *
	 * @param data byte[]
	 * @return 十六进制char[]
	 */
	public static char[] encode(byte[] data) {
		return encode(data, true);
	}
	
	/**
	 * 将字节数组转换为十六进制字符数组
	 *
	 * @param data     byte[]
	 * @param toLowerCase {@code true} 传换成小写格式 ， {@code false} 传换成大写格式
	 * @return 十六进制char[]
	 */
	public static char[] encode(byte[] data, boolean toLowerCase) {
		char[] toDigits = toLowerCase ? CHARS_HEX_DIGITS_LOWER : CHARS_HEX_DIGITS_UPPER;
		final int len = data.length;
		final char[] out = new char[len << 1];//len*2
		// two characters from the hex value.
		for (int i = 0, j = 0; i < len; i++) {
			out[j++] = toDigits[(0xF0 & data[i]) >>> 4];// 高位
			out[j++] = toDigits[0x0F & data[i]];// 低位
		}
		return out;
	}
	
	/**
	 * 转为16进制字符串
	 *
	 * @param value int值
	 * @return 16进制字符串
	 */
	public static String toHex(int value) {
		return Integer.toHexString(value);
	}
	
	/**
	 * 将指定int值转换为Unicode字符串形式，常用于特殊字符（例如汉字）转Unicode形式<br>
	 * 转换的字符串如果u后不足4位，则前面用0填充，例如：
	 *
	 * <pre>
	 * '你' =》\u4f60
	 * </pre>
	 *
	 * @param value int值，也可以是char
	 * @return Unicode表现形式
	 */
	public static String toUnicodeHex(int value) {
		final StringBuilder builder = new StringBuilder(6);

		builder.append("\\u");
		String hex = toHex(value);
		int len = hex.length();
		if (len < 4) {
			builder.append("0000", 0, 4 - len);// 不足4位补0
		}
		builder.append(hex);

		return builder.toString();
	}
}
