package io.github.openguava.jvtool.lang.serialization;

import java.io.UnsupportedEncodingException;

import io.github.openguava.jvtool.lang.exception.SerializationException;

/**
 * 字符串序列化
 * 
 * @author openguava
 *
 */
public class StringSerializer implements Serializer<String, byte[]> {

	private static final String DEFAULT_CHARSET = "UTF-8";

	/**
	 * Refer to
	 * https://docs.oracle.com/javase/8/docs/technotes/guides/intl/encoding.doc.html
	 * UTF-8, UTF-16, UTF-32, ISO-8859-1, GBK, Big5, etc
	 */
	private String charset = DEFAULT_CHARSET;

	public String getCharset() {
		return this.charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	@Override
	public byte[] serialize(String str) throws SerializationException {
		try {
			return (str == null ? null : str.getBytes(this.getCharset()));
		} catch (UnsupportedEncodingException e) {
			throw new SerializationException("serialize error, string=" + str, e);
		}
	}

	@Override
	public String deserialize(byte[] bytes) throws SerializationException {
		try {
			return (bytes == null ? null : new String(bytes, this.getCharset()));
		} catch (UnsupportedEncodingException e) {
			throw new SerializationException("deserialize error", e);
		}
	}
}
