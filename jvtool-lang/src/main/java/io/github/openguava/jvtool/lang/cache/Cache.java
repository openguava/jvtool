package io.github.openguava.jvtool.lang.cache;

import java.io.Serializable;
import java.util.Set;
import java.util.function.Supplier;

/**
 * 缓存接口
 * @author openguava
 */
public interface Cache extends Serializable {

	/**
	 * 获取缓存名称
	 * @return
	 */
	String getName();
	
	/**
	 * 获取缓存值
	 * @param key 键
	 * @return
	 */
	Object get(Object key);
	
	/**
	 * 获取缓存值
	 * @param key 键
	 * @param valueLoader 如果不存在回调方法，用于生产值对象
	 * @return
	 */
	Object get(Object key, Supplier<Object> valueLoader);
	
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	void put(Object key, Object value);
	
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param ttl 生存时间(毫秒)
	 * @return
	 */
	void put(Object key, Object value, long ttl);
	
	/**
	 * 移除缓存
	 * @param key 键
	 * @return
	 */
	boolean remove(Object key);
	
	/**
	 * 获取匹配 key对应的缓存key集合
	 * @param pattern
	 * @return
	 */
	Set<Object> keys(Object pattern);
	
	/**
	 * 获取匹配key对应的缓存数量
	 * @param pattern
	 * @return
	 */
	long size(Object pattern);
	
	/**
	 * 清空缓存
	 */
	void clear();
}
