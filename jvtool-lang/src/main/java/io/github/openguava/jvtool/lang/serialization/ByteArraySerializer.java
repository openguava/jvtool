package io.github.openguava.jvtool.lang.serialization;

import io.github.openguava.jvtool.lang.exception.SerializationException;

/**
 * 字节数组序列化
 * @author openguava
 *
 */
public class ByteArraySerializer implements Serializer<byte[], byte[]> {

	@Override
	public byte[] serialize(byte[] bytes) throws SerializationException {
		return bytes;
	}

	@Override
	public byte[] deserialize(byte[] value) throws SerializationException {
		return value;
	}

}
