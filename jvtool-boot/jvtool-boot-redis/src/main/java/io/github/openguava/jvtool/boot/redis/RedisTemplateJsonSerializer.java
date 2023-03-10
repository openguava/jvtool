package io.github.openguava.jvtool.boot.redis;

import java.nio.charset.Charset;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;

import io.github.openguava.jvtool.lang.constant.CharsetConstants;

/**
 * redis 序列化
 * @author openguava
 *
 * @param <T>
 */
public class RedisTemplateJsonSerializer<T> implements RedisSerializer<T> {

	/** 类型 */
	private Class<T> clazz;
	
	public Class<T> getClazz() {
		return this.clazz;
	}
	
	/** 字符集 */
	private Charset charset = CharsetConstants.CHARSET_UTF_8;
	
	public Charset getCharset() {
		return this.charset;
	}
	
	public void setCharset(Charset charset) {
		this.charset = charset;
	}
	
	public RedisTemplateJsonSerializer(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public byte[] serialize(T t) throws SerializationException {
		if(t == null) {
			return new byte[0];
		}
		return JSON.toJSONString(t, JSONWriter.Feature.WriteClassName).getBytes(this.charset);
	}

	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		if(bytes == null || bytes.length == 0) {
			return null;
		}
		String json = new String(bytes, this.charset);
		return JSON.parseObject(json, this.clazz, JSONReader.Feature.SupportAutoType);
	}
}
