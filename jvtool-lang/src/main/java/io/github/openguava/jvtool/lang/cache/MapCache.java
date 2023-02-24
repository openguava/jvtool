package io.github.openguava.jvtool.lang.cache;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;

/**
 * map类型缓存，读写锁控制，,不支持超时控制
 * @author openguava
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
public class MapCache<K, V> extends AbstractCache<K, V> {
	
	private static final long serialVersionUID = 1L;

	/** 原生缓存 */
	protected final ConcurrentMap<K, V> map;
	
	@Override
	public Object getNativeCache() {
		return this.map;
	}
	
	/**
	 * 初始化
	 */
	public MapCache(ConcurrentMap<K, V> nativeCache) {
		super();
		this.map = nativeCache;
	}
	
	/**
	 * 初始化
	 * @param name 缓存名
	 */
	public MapCache(String name, ConcurrentMap<K, V> nativeCache) {
		super(name);
		this.map = nativeCache;
	}
	
	/**
	 * 从缓存池中查找值
	 * 
	 * @param key 键
	 * @return 值
	 */
	@Override
	public V get(Object key) {
		return this.map.get(key);
	}
	
	/**
	 * 从缓存中获得对象，当对象不在缓存中或已经过期返回Func0回调产生的对象
	 * 
	 * @param key 键
	 * @param supplier 如果不存在回调方法，用于生产值对象
	 * @return 值对象
	 */
	@Override
	public V get(K key, Callable<V> callable) {
		// get
		V val = this.map.get(key);
		if(val != null) {
			return val;
		}
		// callable
		if (callable != null) {
			try {
				V newVal = callable.call();
				val = this.map.putIfAbsent(key, newVal);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return val;
	}
	
	@Override
	public V put(K key, V value){
		return this.put(key, value, this.getTimeout());
	}
	
	@Override
	public V put(K key, V value, long ttl) {
		return this.map.put(key, value);
	}

	@Override
	public V remove(K key) {
		return this.map.remove(key);
	}

	@Override
	public Set<K> keys(K pattern) {
		return this.map.keySet();
	}

	@Override
	public long size(K pattern) {
		return this.map.size();
	}
	
	@Override
	public void clear() {
		this.map.clear();
	}
}
