package io.github.openguava.jvtool.lang.cache;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
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
	Object get(String key);

	/**
	 * 获取缓存值
	 * @param key 键
	 * @param valueLoader 如果不存在回调方法，用于生产值对象
	 * @return
	 */
	Object get(String key, Supplier<Object> valueLoader);

	/**
	 * 获取字节数组缓存值
	 * @param key
	 * @return
	 */
	byte[] getBytes(String key);
	
	/**
	 * 获取缓存值对象
	 * @param key
	 * @param clazz
	 * @return
	 */
	<T> T getItem(String key, Class<T> clazz);
	
	/**
	 * 获取缓存值列表
	 * @param <T>
	 * @param key
	 * @param clazz
	 * @return
	 */
	<T> List<T> getList(String key, Class<T> clazz);
	
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	void put(String key, Object value);
	
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param ttl 生存时间(毫秒)
	 * @return
	 */
	void put(String key, Object value, long ttl);
	
	/**
	 * 移除缓存
	 * @param key 键
	 * @return
	 */
	boolean remove(String key);
	
	/**
	 * 批量移除缓存
	 * @param keys 键集合
	 * @return
	 */
	long removes(Collection<String> keys);
	
	/**
	 * 指定缓存是否存在
	 * @param key
	 * @return
	 */
	boolean exists(String key);
	
	/**
	 * 获取匹配 key对应的缓存key集合
	 * @param pattern
	 * @return
	 */
	Set<String> keys(String pattern);
	
	/**
	 * 获取匹配key对应的缓存数量
	 * @param pattern
	 * @return
	 */
	long size(String pattern);
	
	/**
	 * 清空缓存
	 */
	void clear();
}
