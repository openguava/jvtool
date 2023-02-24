package io.github.openguava.jvtool.lang.cache.timed;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import io.github.openguava.jvtool.lang.cache.ValueWrapper;

/**
 * 支持超时控制的缓存值封装
 * @author openguava
 *
 * @param <T>
 */
public class TimedValueWrapper<K, T> implements ValueWrapper<T> {

	private K key;
	
	public K getKey() {
		return this.key;
	}
	
	/**
	 * 对象值
	 */
	private T value;
	
	@Override
	public T get() {
		return this.value;
	}
	
	/**
	 * 对象存活时长，小于1表示永久存活
	 */
	protected final long ttl;
	
	public long getTtl() {
		return this.ttl;
	}
	
	/**
	 * 上次访问时间
	 */
	protected volatile long lastAccess;
	
	public long getLastAccess() {
		return this.lastAccess;
	}
	
	
	/**
	 * 访问次数
	 */
	protected AtomicLong accessCount = new AtomicLong();
	
	/**
	 * 初始化
	 * @param key
	 * @param value
	 */
	public TimedValueWrapper(K key, T value) {
		this(key, value, 0L);
	}
	
	/**
	 * 初始化
	 * @param key
	 * @param value
	 * @param ttl
	 */
	public TimedValueWrapper(K key, T value, long ttl) {
		this.key = key;
		this.value = value;
		this.ttl = ttl;
		this.lastAccess = System.currentTimeMillis();
	}
	
	/**
	 * 判断是否过期
	 *
	 * @return 是否过期
	 */
	public boolean isExpired() {
		if(this.ttl < 1) {
			return false;
		}
		// 此处不考虑时间回拨
		return (System.currentTimeMillis() - this.lastAccess) > this.ttl;
	}
	
	/**
	 * 获取过期时间，返回{@code null}表示永不过期
	 *
	 * @return 此对象的过期时间，返回{@code null}表示永不过期
	 */
	public Date getExpiredTime(){
		if(this.ttl < 1) {
			return null;
		}
		return new Date(this.lastAccess + this.ttl);
	}
	
	@Override
	public String toString() {
		return "TimeValueWrapper [value=" + (this.value != null ? this.value : "") + ", lastAccess=" + lastAccess + ", ttl=" + ttl + "]";
	}
}
