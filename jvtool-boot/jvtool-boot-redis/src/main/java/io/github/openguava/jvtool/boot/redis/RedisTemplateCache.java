package io.github.openguava.jvtool.boot.redis;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import io.github.openguava.jvtool.lang.cache.AbstractCache;

public class RedisTemplateCache extends AbstractCache {

	private static final long serialVersionUID = 1L;
	
	/** redisTemplate */
	private RedisTemplate<Object, Object> redisTemplate;	
	
	public RedisTemplateCache(String name, RedisTemplate<Object, Object> redisTemplate) {
		super(name);
		this.redisTemplate = redisTemplate;
	}

	@Override
	public Object get(Object key) {
		return this.redisTemplate.opsForValue().get(key);
	}

	@Override
	public Object get(Object key, Supplier<Object> valueLoader) {
		Object val = this.redisTemplate.opsForValue().get(key);
		if(val == null && valueLoader != null) {
			val = valueLoader.get();
			this.redisTemplate.opsForValue().set(key, val);
		}
		return val;
	}

	@Override
	public void put(Object key, Object value) {
		this.redisTemplate.opsForValue().set(key, value);
	}

	@Override
	public void put(Object key, Object value, long ttl) {
		if(ttl > 0L) {
			this.redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.MILLISECONDS);
		} else {
			this.redisTemplate.opsForValue().set(key, value);
		}
	}

	@Override
	public boolean remove(Object key) {
		Boolean ret = this.redisTemplate.delete(key);
		return (ret != null && ret.booleanValue()) ? true : false;
	}

	@Override
	public Set<Object> keys(Object pattern) {
		return this.redisTemplate.keys(pattern);
	}

	@Override
	public long size(Object pattern) {
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
