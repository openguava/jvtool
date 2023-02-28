package io.github.openguava.jvtool.lang.cache.timed;

import java.io.Closeable;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import io.github.openguava.jvtool.lang.cache.AbstractCache;
import io.github.openguava.jvtool.lang.map.SafeConcurrentHashMap;

public class TimedCache extends AbstractCache implements Closeable {

	private static final long serialVersionUID = 1L;
	
	private final ConcurrentMap<Object, TimedValueWrapper<Object, Object>> map = new SafeConcurrentHashMap<>();
	
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
		super();
	}
	
	/**
	 * 初始化
	 * @param name
	 */
	public TimedCache(String name) {
		super(name);
	}

	@Override
	public Object get(Object key) {
		synchronized (this.map) {
			TimedValueWrapper<Object, Object> wrapper = this.map.get(key);
			if(wrapper == null) {
				return null;
			}
			if(wrapper.isExpired()) {
				this.map.remove(key);
				return null;
			}
			return wrapper.get();
		}
	}

	@Override
	public Object get(Object key, Supplier<Object> valueLoader) {
		TimedValueWrapper<Object, Object> wrapper = this.map.computeIfAbsent(key, x -> new TimedValueWrapper<>(key, valueLoader.get()));
		return wrapper != null ? wrapper.get() : null;
	}

	@Override
	public void put(Object key, Object value) {
		this.map.put(key, new TimedValueWrapper<>(key, value));
	}

	@Override
	public void put(Object key, Object value, long ttl) {
		this.map.put(key, new TimedValueWrapper<>(key, value, ttl));
	}

	@Override
	public boolean remove(Object key) {
		TimedValueWrapper<Object, Object> wrapper = this.map.remove(key);
		return wrapper != null;
	}

	@Override
	public Set<Object> keys(Object pattern) {
		return this.map.keySet();
	}

	@Override
	public long size(Object pattern) {
		return this.map.size();
	}

	@Override
	public void clear() {
		this.map.clear();
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
