package io.github.openguava.jvtool.lang.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import io.github.openguava.jvtool.lang.exception.SerializationException;
import io.github.openguava.jvtool.lang.util.ArrayUtils;

/**
 * 对象二进制序列化
 * 
 * @author openguava
 *
 */
public class ObjectSerializer implements Serializer<Object, byte[]> {

	private static final int BYTE_ARRAY_OUTPUT_STREAM_SIZE = 128;

	@Override
	public byte[] serialize(Object object) throws SerializationException {
		byte[] result = new byte[0];
		if (object == null) {
			return result;
		}
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream(BYTE_ARRAY_OUTPUT_STREAM_SIZE);
		// 是否支持序列化
		if (!(object instanceof Serializable)) {
			throw new SerializationException("requires a Serializable payload " + "but received an object of type ["
					+ object.getClass().getName() + "]");
		}
		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream)) {
			objectOutputStream.writeObject(object);
			objectOutputStream.flush();
			result = byteStream.toByteArray();
		} catch (IOException e) {
			throw new SerializationException("Failed to serialize object of type: " + object.getClass(), e);
		}
		return result;
	}

	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		Object result = null;
		if (ArrayUtils.isEmpty(bytes)) {
			return result;
		}
		try (ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
			result = objectInputStream.readObject();
		} catch (IOException e) {
			throw new SerializationException("Failed to deserialize object", e);
		} catch (ClassNotFoundException e) {
			throw new SerializationException("Failed to deserialize object type", e);
		}
		return result;
	}
}
