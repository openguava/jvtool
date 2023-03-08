package io.github.openguava.jvtool.lang.util;

import java.util.Map;
import java.util.function.Function;

/**
 * map 工具类
 * @author openguava
 *
 */
public class MapUtils {

	/**
	 * 获取map指定键对应值
	 * @param <K>
	 * @param <V>
	 * @param map map
	 * @param key 键
	 * @return
	 */
	public static <K, V> V get(Map<K, V> map, K key) {
		if(map == null) {
			return null;
		}
		return map.get(key);
	}
	
	/**
	 * 获取map指定键对应值
	 * @param <K>
	 * @param <V>
	 * @param map map
	 * @param key 键
	 * @param defaultValue 默认值
	 * @return
	 */
	public static <K, V> V get(Map<K, V> map, K key, V defaultValue) {
		if(map == null) {
			return null;
		}
		return map.getOrDefault(key, defaultValue);
	}
	
	/**
	 * 获取map指定键对应值
	 * @param <K>
	 * @param <V>
	 * @param map map
	 * @param key 键
	 * @param mappingFunction 值不存在的处理
	 * @return
	 */
	public static <K, V> V getIfAbsent(Map<K, V> map, K key, Function<? super K, ? extends V> mappingFunction) {
		if(map == null) {
			return null;
		}
		return map.computeIfAbsent(key, mappingFunction);
	}
	
	/**
	 * 获取map指定键对应字符串值
	 * @param <K>
	 * @param <V>
	 * @param map map
	 * @param key 键
	 * @return
	 */
	public static <K, V> String getString(Map<K, V> map, K key) {
		if(map == null) {
			return null;
		}
		return ConvertUtils.toStr(map.get(key));
	}
	
	/**
	 * 获取map指定键对应长整形值
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param key
	 * @return
	 */
	public static <K, V> Long getLong(Map<K, V> map, K key) {
		if(map == null) {
			return null;
		}
		return ConvertUtils.toLong(map.get(key));
	}
	
	/**
	 * 获取map指定键对应整形值
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param key
	 * @return
	 */
	public static <K, V> Integer getInteger(Map<K, V> map, K key) {
		if(map == null) {
			return null;
		}
		return ConvertUtils.toInt(map.get(key));
	}
	
	/**
	 * 设置map指定键对应值
	 * @param <K>
	 * @param <V>
	 * @param map map
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public static <K, V> V put(Map<K, V> map, K key, V value) {
		if(map == null) {
			return null;
		}
		return map.put(key, value);
	}
	
	/**
	 * 如果值不存在,则设置map指定键对应值
	 * @param <K>
	 * @param <V>
	 * @param map map
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public static <K, V> V putIfAbsent(Map<K, V> map, K key, V value) {
		if(map == null) {
			return null;
		}
		return map.putIfAbsent(key, value);
	}
}
