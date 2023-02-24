package io.github.openguava.jvtool.lang.serialization;

import io.github.openguava.jvtool.lang.exception.SerializationException;

public interface Serializer<T, V> {

	V serialize(T t) throws SerializationException;
	
	T deserialize(V value) throws SerializationException;
}
