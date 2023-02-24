package io.github.openguava.jvtool.lang.cache;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * 缓存接口
 * @author openguava
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
public interface Cache<K, V> extends Serializable {

	/**
	 * 获取缓存名称
	 * @return
	 */
	String getName();
	
	/**
	 * 获取原生缓存提供者
	 * @return
	 */
	Object getNativeCache();
	
	/**
	 * 获取缓存封装对象
	 * @param key 键
	 * @return
	 */
	V get(K key);
	
	/**
	 * 获取缓存值
	 * @param key 键
	 * @param valueLoader 如果不存在回调方法，用于生产值对象
	 * @return
	 */
	V get(K key, Callable<V> valueLoader);
	
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	V put(K key, V value);
	
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param ttl 生存时间(毫秒)
	 * @return
	 */
	V put(K key, V value, long ttl);
	
	/**
	 * 移除缓存
	 * @param key 键
	 * @return
	 */
	V remove(K key);
	
	/**
	 * 获取匹配 key对应的缓存key集合
	 * @param pattern
	 * @return
	 */
	Set<K> keys(K pattern);
	
	/**
	 * 获取匹配key对应的缓存数量
	 * @param pattern
	 * @return
	 */
	long size(K pattern);
	
	/**
	 * 清空缓存
	 */
	void clear();
}
