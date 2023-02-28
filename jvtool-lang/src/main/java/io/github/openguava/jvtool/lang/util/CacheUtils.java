package io.github.openguava.jvtool.lang.util;

import java.util.Set;
import java.util.function.Supplier;

import io.github.openguava.jvtool.lang.cache.AbstractCacheManager;
import io.github.openguava.jvtool.lang.cache.Cache;
import io.github.openguava.jvtool.lang.cache.timed.TimedCacheManager;
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
	public static Object get(Object key) {
		return getCache().get(key);
	}
	
	/**
	 * 获取缓存值
	 * @param key 键
	 * @param valueLoader 如果不存在回调方法，用于生产值对象
	 * @return
	 */
	public static Object get(Object key, Supplier<Object> valueLoader) {
		return getCache().get(key, valueLoader);
	}
	
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public static void put(Object key, Object value) {
		getCache().put(key, value);;
	}
	
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param ttl 生存时间(毫秒)
	 * @return
	 */
	public static void put(Object key, Object value, long ttl) {
		getCache().put(key, value, ttl);
	}
	
	/**
	 * 移除缓存
	 * @param key 键
	 * @return
	 */
	public static boolean remove(Object key) {
		return getCache().remove(key);
	}
	
	/**
	 * 获取匹配 key对应的缓存key集合
	 * @param pattern
	 * @return
	 */
	public static Set<Object> keys(Object pattern) {
		return getCache().keys(pattern);
	}
	
	/**
	 * 获取匹配key对应的缓存数量
	 * @param pattern
	 * @return
	 */
	public static long size(Object pattern) {
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
