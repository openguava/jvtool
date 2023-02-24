package io.github.openguava.jvtool.lang.cache.timed;

import java.io.Closeable;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import io.github.openguava.jvtool.lang.cache.AbstractCache;
import io.github.openguava.jvtool.lang.cache.MapCache;

public class TimedCache<K, V> extends AbstractCache<K, V> implements Closeable {

	private static final long serialVersionUID = 1L;
	
	private MapCache<K, TimedValueWrapper<K, V>> nativeCache;
	
	@Override
	public Object getNativeCache() {
		return this.nativeCache;
	}
	
	/**
	 * 定时服务
	 */
	private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
	
	/** 正在执行的定时任务 */
	private ScheduledFuture<?> scheduledFuture;
	
	/**
	 * 初始化
	 */
	public TimedCache() {
		this.nativeCache = new MapCache<>(new ConcurrentHashMap<>());
	}
	
	/**
	 * 初始化
	 * @param name
	 */
	public TimedCache(String name) {
		this.nativeCache = new MapCache<>(name, new ConcurrentHashMap<>());
	}

	@Override
	public V get(K key) {
		synchronized (this.nativeCache) {
			TimedValueWrapper<K, V> wrapper = this.nativeCache.get(key);
			if(wrapper == null) {
				return null;
			}
			if(wrapper.isExpired()) {
				this.nativeCache.remove(key);
				return null;
			}
			return wrapper.get();
		}
	}

	@Override
	public V get(K key, Callable<V> valueLoader) {
		TimedValueWrapper<K, V> wrapper = this.nativeCache.get(key, () -> new TimedValueWrapper<>(key, valueLoader.call()));
		return wrapper != null ? wrapper.get() : null;
	}

	@Override
	public V put(K key, V value) {
		TimedValueWrapper<K, V> wrapper = this.nativeCache.put(key, new TimedValueWrapper<>(key, value));
		return wrapper != null ? wrapper.get() : value;
	}

	@Override
	public V put(K key, V value, long ttl) {
		TimedValueWrapper<K, V> wrapper = this.nativeCache.put(key, new TimedValueWrapper<>(key, value, ttl));
		return wrapper != null ? wrapper.get() : value;
	}

	@Override
	public V remove(K key) {
		TimedValueWrapper<K, V> wrapper = this.nativeCache.remove(key);
		return wrapper != null ? wrapper.get() : null;
	}

	@Override
	public Set<K> keys(K pattern) {
		return this.nativeCache.keys(pattern);
	}

	@Override
	public long size(K pattern) {
		return this.nativeCache.size(pattern);
	}

	@Override
	public void clear() {
		this.nativeCache.clear();
	}
	
	/**
	 * 启动定时任务
	 * @param delay
	 */
	public void startSchedule(int delay) {
		if(this.scheduledFuture != null) {
			this.scheduledFuture.cancel(true);
			this.scheduledFuture = null;
		}
		this.scheduledFuture = this.scheduledExecutorService.scheduleAtFixedRate(this::schedule, delay, delay, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * 停止定时任务
	 */
	public void stopSchedule() {
		// scheduledFuture
		if(this.scheduledFuture != null) {
			this.scheduledFuture.cancel(true);
			this.scheduledFuture = null;
		}
	}
	
	/**
	 * 定时任务
	 */
	protected void schedule() {
		
	}

	@Override
	public void close() throws IOException {
		if(this.scheduledExecutorService != null) {
			this.scheduledExecutorService.shutdownNow();
		}
	}
}
