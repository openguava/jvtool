package io.github.openguava.jvtool.lang.cache.timed;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import io.github.openguava.jvtool.lang.cache.AbstractCache;
import io.github.openguava.jvtool.lang.constant.CharsetConstants;
import io.github.openguava.jvtool.lang.map.SafeConcurrentHashMap;
import io.github.openguava.jvtool.lang.util.ByteUtils;
import io.github.openguava.jvtool.lang.util.CollectionUtils;
import io.github.openguava.jvtool.lang.util.JsonUtils;
import io.github.openguava.jvtool.lang.util.ObjectUtils;
import io.github.openguava.jvtool.lang.util.RegexUtils;
import io.github.openguava.jvtool.lang.util.StringUtils;

public class TimedCache extends AbstractCache implements Closeable {

	private static final long serialVersionUID = 1L;
	
	private final ConcurrentMap<String, TimedValueWrapper<String, Object>> map = new SafeConcurrentHashMap<>();
	
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
	public Object get(String key) {
		synchronized (this.map) {
			TimedValueWrapper<String, Object> wrapper = this.map.get(key);
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
	public Object get(String key, Supplier<Object> valueLoader) {
		TimedValueWrapper<String, Object> wrapper = this.map.computeIfAbsent(key, x -> new TimedValueWrapper<>(key, valueLoader.get()));
		return wrapper != null ? wrapper.get() : null;
	}
	
	@Override
	public byte[] getBytes(String key) {
		Object obj = this.get(key);
		if(obj == null) {
			return null;
		}
		if(obj instanceof byte[]) {
			return (byte[])obj;
		}
		String str = StringUtils.toString(obj, CharsetConstants.CHARSET_UTF_8);
		return ByteUtils.stringToBytes(str, CharsetConstants.CHARSET_UTF_8);
	}
	
	@Override
	public <T> T getItem(String key, Class<T> clazz) {
		byte[] bytes = this.getBytes(key);
		if(bytes == null) {
			return null;
		}
		return JsonUtils.parseObject(ByteUtils.bytesToString(bytes, CharsetConstants.CHARSET_UTF_8), clazz);
	}
	
	@Override
	public <T> List<T> getList(String key, Class<T> clazz) {
		byte[] bytes = this.getBytes(key);
		if(bytes == null) {
			return null;
		}
		return JsonUtils.parseArray(ByteUtils.bytesToString(bytes, CharsetConstants.CHARSET_UTF_8), clazz);
	}

	@Override
	public void put(String key, Object value) {
		this.map.put(key, new TimedValueWrapper<>(key, value));
	}

	@Override
	public void put(String key, Object value, long ttl) {
		this.map.put(key, new TimedValueWrapper<>(key, value, ttl));
	}

	@Override
	public boolean remove(String key) {
		return this.map.remove(key) != null;
	}
	
	@Override
	public long removes(Collection<String> keys) {
		if(ObjectUtils.isEmpty(keys)) {
			return 0L;
		}
		long count = 0L;
		for (Object key : keys) {
			if(this.map.remove(key) != null) {
				count++;
			}
		}
		return count;
	}
	
	@Override
	public boolean exists(String key) {
		return this.map.containsKey(key);
	}

	@Override
	public Set<String> keys(String pattern) {
		Set<String> keys = this.map.keySet();
		if(StringUtils.isEmpty(pattern)) {
			return keys;
		}
		return CollectionUtils.toSet(CollectionUtils.filter(keys, x -> x.equalsIgnoreCase(pattern) || RegexUtils.simpleMatch(new String[] { pattern }, x)));
	}

	@Override
	public long size(String pattern) {
		Set<String> keys = this.map.keySet();
		if(StringUtils.isEmpty(pattern)) {
			return keys.size();
		}
		return CollectionUtils.count(keys, x -> x.equalsIgnoreCase(pattern) || RegexUtils.simpleMatch(new String[] { pattern }, x));
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
	
	public static void main(String[] args) {
		
	}
}
