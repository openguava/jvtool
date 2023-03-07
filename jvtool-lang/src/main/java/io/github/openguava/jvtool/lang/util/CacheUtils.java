package io.github.openguava.jvtool.lang.util;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import io.github.openguava.jvtool.lang.cache.AbstractCacheManager;
import io.github.openguava.jvtool.lang.cache.Cache;
import io.github.openguava.jvtool.lang.cache.timed.TimedCacheManager;
import io.github.openguava.jvtool.lang.constant.CharsetConstants;
import io.github.openguava.jvtool.lang.constant.StringConstants;

/**
 * 缓存工具类
 * @author openguava
 *
 */
public class CacheUtils {
	
	/** 默认缓存名称 */
	private static volatile String defaultCacheName = StringConstants.STRING_EMPTY;
	
	public static String getDefaultCacheName() {
		return CacheUtils.defaultCacheName;
	}
	
	public static void setDefaultCacheName(String defaultCacheName) {
		synchronized (CacheUtils.class) {
			CacheUtils.defaultCacheName = defaultCacheName;
		}
	}

	/** 默认缓存管理器实例 */
	private static volatile AbstractCacheManager defaultCacheManager;
	
	public static AbstractCacheManager getDefaultCacheManager() {
		if(CacheUtils.defaultCacheManager == null) {
			synchronized (CacheUtils.class) {
				if(CacheUtils.defaultCacheManager == null) {
					CacheUtils.defaultCacheManager = TimedCacheManager.getInstance();
				}
			}
		}
		return CacheUtils.defaultCacheManager;
	}
	
	public static void setDefaultCacheManager(AbstractCacheManager cacheManager) {
		synchronized (CacheUtils.class) {
			CacheUtils.defaultCacheManager = cacheManager;
		}
	}
	
	/**
	 * 获取默认缓存对象
	 * @return
	 */
	public static Cache getCache() {
		return getDefaultCacheManager().getCache(getDefaultCacheName());
	}
	
	/**
	 * 获取缓存值
	 * @param key 键
	 * @return
	 */
	public static Object get(String key) {
		return getCache().get(key);
	}
	
	/**
	 * 获取缓存值
	 * @param key 键
	 * @param valueLoader 如果不存在回调方法，用于生产值对象
	 * @return
	 */
	public static Object get(String key, Supplier<Object> valueLoader) {
		return getCache().get(key, valueLoader);
	}
	
	/**
	 * 获取字节数组缓存值
	 * @param key 键
	 * @return
	 */
	public static byte[] getBytes(String key) {
		return getCache().getBytes(key);
	}
	
	/**
	 * 获取字符串缓存值
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		byte[] bytes = getCache().getBytes(key);
		return bytes == null ? null : new String(bytes, CharsetConstants.CHARSET_UTF_8);
	}
	
	/**
	 * 根据类型获取缓存值对象
	 * @param <T>
	 * @param key
	 * @param clazz
	 * @return
	 */
	public static <T> T getItem(String key, Class<T> clazz) {
		return getCache().getItem(key, clazz);
	}
	
	/**
	 * 根据类型获取缓存值列表
	 * @param <T>
	 * @param key
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> getList(String key, Class<T> clazz) {
		return getCache().getList(key, clazz);
	}

	
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public static void put(String key, Object value) {
		getCache().put(key, value);;
	}
	
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param ttl 生存时间(毫秒)
	 * @return
	 */
	public static void put(String key, Object value, long ttl) {
		getCache().put(key, value, ttl);
	}
	
	/**
	 * 移除缓存
	 * @param key 键
	 * @return
	 */
	public static boolean remove(String key) {
		return getCache().remove(key);
	}
	
	/**
	 * 批量移除缓存
	 * @param keys
	 * @return
	 */
	public static long removes(Collection<String> keys) {
		return getCache().removes(keys);
	}
	
	/**
	 * 指定的缓存是否存在
	 * @param key
	 * @return
	 */
	public static boolean exists(String key) {
		return getCache().exists(key);
	}
	
	/**
	 * 获取匹配 key对应的缓存key集合
	 * @param pattern
	 * @return
	 */
	public static Set<String> keys(String pattern) {
		return getCache().keys(pattern);
	}
	
	/**
	 * 获取匹配key对应的缓存数量
	 * @param pattern
	 * @return
	 */
	public static long size(String pattern) {
		return getCache().size(pattern);
	}
	
	/**
	 * 清空缓存
	 */
	public static void clear() {
		getCache().clear();
	}
	
	public static void main(String[] args) {
		
		CacheUtils.put("user", "admin");
		
		Object obj = CacheUtils.get("user");
		
		System.out.println(obj.getClass().getTypeName());
	}
}
