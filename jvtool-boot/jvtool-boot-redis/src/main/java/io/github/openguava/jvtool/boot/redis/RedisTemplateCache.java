package io.github.openguava.jvtool.boot.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import io.github.openguava.jvtool.lang.cache.AbstractCache;
import io.github.openguava.jvtool.lang.util.CollectionUtils;
import io.github.openguava.jvtool.lang.util.ObjectUtils;
import io.github.openguava.jvtool.lang.util.StringUtils;

public class RedisTemplateCache extends AbstractCache {

	private static final long serialVersionUID = 1L;
	
	/** redisTemplate */
	private RedisTemplate<String, Object> redisTemplate;	
	
	public RedisTemplateCache(String name, RedisTemplate<String, Object> redisTemplate) {
		super(name);
		this.redisTemplate = redisTemplate;
	}

	@Override
	public Object get(String key) {
		return this.redisTemplate.opsForValue().get(key);
	}

	@Override
	public Object get(String key, Supplier<Object> valueLoader) {
		Object val = this.redisTemplate.opsForValue().get(key);
		if(val == null && valueLoader != null) {
			val = valueLoader.get();
			this.redisTemplate.opsForValue().set(key, val);
		}
		return val;
	}
	
	@Override
	public byte[] getBytes(String key) {
		final byte[] keyBytes = StringUtils.toBytes(key);
		return this.redisTemplate.execute(new RedisCallback<byte[]>() {
			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.get(keyBytes);
			}
		});
	}
	
	@Override
	public <T> T getItem(String key, Class<T> clazz) {
		byte[] data = this.getBytes(key);
		return new RedisTemplateJsonSerializer<T>(clazz).deserialize(data);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getList(String key, Class<T> clazz) {
		byte[] data = this.getBytes(key);
		ArrayList<T> list = new ArrayList<T>();
		Class<? extends ArrayList> listClazz = list.getClass();
		list = new RedisTemplateJsonSerializer<>(listClazz).deserialize(data);
		return CollectionUtils.toList(list);
	}

	@Override
	public void put(String key, Object value) {
		this.redisTemplate.opsForValue().set(key, value);
	}

	@Override
	public void put(String key, Object value, long ttl) {
		if(ttl > 0L) {
			this.redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.MILLISECONDS);
		} else {
			this.redisTemplate.opsForValue().set(key, value);
		}
	}

	@Override
	public boolean remove(String key) {
		return ObjectUtils.ifNull(this.redisTemplate.delete(key), false);
	}
	
	@Override
	public long removes(Collection<String> keys) {
		return ObjectUtils.ifNull(this.redisTemplate.delete(keys), 0L);
	}
	
	@Override
	public boolean exists(String key) {
		return ObjectUtils.ifNull(this.redisTemplate.hasKey(key), false);
	}

	@Override
	public Set<String> keys(String pattern) {
		return this.redisTemplate.keys(pattern);
	}

	@Override
	public long size(String pattern) {
		return this.redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.dbSize();
			}
		});
	}

	@Override
	public void clear() {
		this.redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushDb();
				return null;
			}
		});
	}
}
