package io.github.openguava.jvtool.lang.codec.hex;

import io.github.openguava.jvtool.lang.exception.UtilException;
import io.github.openguava.jvtool.lang.util.StringUtils;

/**
 * 十六进制解码
 * @author openguava
 *
 */
public class HexDecoder {

	/**
	 * 将十六进制字符串解码为byte[]
	 *
	 * @param hexStr 十六进制String
	 * @return byte[]
	 */
	public static byte[] decode(String hexStr) {
		return decode((CharSequence) hexStr);
	}
	
	/**
	 * 将十六进制字符数组转换为字节数组
	 *
	 * @param hexData 十六进制字符串
	 * @return byte[]
	 * @throws UtilsException 如果源十六进制字符数组是一个奇怪的长度，将抛出运行时异常
	 */
	public static byte[] decode(CharSequence hexData) {
		if (StringUtils.isEmpty(hexData)) {
			return null;
		}
		hexData = StringUtils.cleanBlank(hexData);
		int len = hexData.length();

		if ((len & 0x01) != 0) {
			hexData = "0" + hexData;
			len = hexData.length();
		}

		final byte[] out = new byte[len >> 1];

		// two characters form the hex value.
		for (int i = 0, j = 0; j < len; i++) {
			int digit = Character.digit(hexData.charAt(j), 16);
			if (digit < 0) {
				throw new UtilException("Illegal hexadecimal character {} at index {}", hexData.charAt(j), j);
			}
			int f = digit << 4;
			j++;
			digit = Character.digit(hexData.charAt(j), 16);
			if (digit < 0) {
				throw new UtilException("Illegal hexadecimal character {} at index {}", hexData.charAt(j), j);
			}
			f = f | digit;
			j++;
			out[i] = (byte) (f & 0xFF);
		}
		return out;
	}
}
