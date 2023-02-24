package io.github.openguava.jvtool.lang.crypto.md5;

import java.security.MessageDigest;

import io.github.openguava.jvtool.lang.constant.CharsetConstants;
import io.github.openguava.jvtool.lang.util.ByteUtils;
import io.github.openguava.jvtool.lang.util.LogUtils;

/**
 * MD5 加密
 * @author openguava
 *
 */
public final class Md5Encoder {

	private static final String DIGEST_ALGORITHM = "MD5";

	public static String encode(String str) {
		byte[] hash = encode(str == null ? null : str.getBytes(CharsetConstants.CHARSET_UTF_8));
		if(hash == null) {
			return null;
		}
		return ByteUtils.bytesToHex(hash, true);
	}

	public static byte[] encode(byte[] bytes) {
		if(bytes == null || bytes.length == 0) {
			return new byte[0];
		}
		MessageDigest algorithm;
		try {
			algorithm = MessageDigest.getInstance(DIGEST_ALGORITHM);
			algorithm.reset();
			algorithm.update(bytes);
			return algorithm.digest();
		} catch (Exception e) {
			LogUtils.error(Md5Encoder.class, e.getMessage(), e);
			return null;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(encode("123456"));
	}
}
