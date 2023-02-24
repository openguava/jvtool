package io.github.openguava.jvtool.lang.util;

import io.github.openguava.jvtool.lang.crypto.bcrypt.BCryptPasswordEncoder;
import io.github.openguava.jvtool.lang.crypto.md5.Md5Encoder;

/**
 * 加密工具类
 * @author openguava
 *
 */
public class CryptoUtils {

	/**
	 * bcrypt算法加密
	 * @param password 真实密码
	 * @return
	 */
	public static String encodeBCrypt(CharSequence rawPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(rawPassword);
	}
	
	/**
	 * bcrypt算法判断密码是否相同
	 *
	 * @param rawPassword     真实密码
	 * @param encodedPassword 加密后字符
	 * @return 结果
	 */
	public static boolean matchesBCrypt(String rawPassword, String encodedPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
	
	/**
	 * MD5 字符串编码为十六进制
	 * @param source
	 * @return
	 */
	public static String encodeMd5(String source) {
		return Md5Encoder.encode(source);
	}
	
	/**
	 * MD5编码为哈希值
	 * @param source
	 * @return
	 */
	public static byte[] encodeMd5(byte[] source) {
		return Md5Encoder.encode(source);
	}
	
	public static void main(String[] args) {
		System.out.println(Md5Encoder.encode("123456"));
	}
}
