package io.github.openguava.jvtool.lang.auth.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.ttl.TransmittableThreadLocal;

import io.github.openguava.jvtool.lang.auth.AuthContext;
import io.github.openguava.jvtool.lang.util.ConvertUtils;

public class SimpleAuthContext implements AuthContext {
	
	/** 实例 */
	private static final SimpleAuthContext instance = new SimpleAuthContext();
	
	public static SimpleAuthContext getInstance() {
		return SimpleAuthContext.instance;
	}

	/** threadLocal */
	private final InheritableThreadLocal<Map<String, Object>> threadLocal = this.createThreadLocal();
	
	/**
	 * 创建 threadLocal(默认为TransmittableThreadLocal)
	 * @return
	 */
	protected InheritableThreadLocal<Map<String, Object>> createThreadLocal() {
		return new TransmittableThreadLocal<>();
	}
	
	/**
	 * 创建 map(默认为ConcurrentHashMap)
	 * @return
	 */
	protected Map<String, Object> createMap() {
		return new ConcurrentHashMap<>();
	}
	
	/**
	 * 获取当前map
	 * @return
	 */
	public Map<String, Object> getMap() {
		Map<String, Object> map = this.threadLocal.get();
		if(map == null) {
			map = this.createMap();
			this.threadLocal.set(map);
		}
		return map;
	}
	
	@Override
	public Object get(String key) {
		return this.getMap().get(key);
	}
	
	@Override
	public <T> T get(String key, Class<T> clazz) {
		Object value = this.getMap().get(key);
		if(value == null) {
			return null;
		}
		return ConvertUtils.toObject(value, clazz);
	}

	@Override
	public void set(String key, Object value) {
		Map<String, Object> map = this.getMap();
		if(value == null && map instanceof ConcurrentHashMap) {
			map.remove(key);
		} else {
			map.put(key, value);
		}
	}

	@Override
	public void clear() {
		this.threadLocal.remove();
	}
}
