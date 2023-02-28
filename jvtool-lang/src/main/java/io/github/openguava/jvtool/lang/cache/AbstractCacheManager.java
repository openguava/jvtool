package io.github.openguava.jvtool.lang.cache;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 抽象 CacheManager
 * @author openguava
 *
 */
public class AbstractCacheManager implements CacheManager {

	/** 缓存Map集合 */
	private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>(16);
	
	/** 缓存名集合 */
	private volatile Set<String> cacheNames = Collections.emptySet();
	
	@Override
	public Collection<String> getCacheNames() {
		return this.cacheNames;
	}
	
	@Override
	public Cache getCache(String name) {
		// Quick check for existing cache...
		Cache cache = this.cacheMap.get(name);
		if (cache != null) {
			return cache;
		}
		// The provider may support on-demand cache creation...
		Cache missingCache = this.getMissingCache(name);
		if(missingCache == null) {
			return cache;
		}
		// Fully synchronize now for missing cache registration
		synchronized (this.cacheMap) {
			cache = this.cacheMap.get(name);
			if (cache == null) {
				cache = decorateCache(missingCache);
				this.cacheMap.put(name, cache);
				updateCacheNames(name);
			}
		}
		return cache;
	}
	
	/**
	 * 查找缓存对象
	 * @param name
	 * @return
	 */
	protected final Cache lookupCache(String name) {
		return this.cacheMap.get(name);
	}

	/**
	 * 添加缓存对象
	 * @param cache
	 */
	protected final void addCache(Cache cache) {
		String name = cache.getName();
		synchronized (this.cacheMap) {
			if (this.cacheMap.put(name, decorateCache(cache)) == null) {
				updateCacheNames(name);
			}
		}
	}

	/**
	 * 更新缓存名集合
	 * @param name
	 */
	private void updateCacheNames(String name) {
		Set<String> cacheNames = new LinkedHashSet<>(this.cacheNames.size() + 1);
		cacheNames.addAll(this.cacheNames);
		cacheNames.add(name);
		this.cacheNames = Collections.unmodifiableSet(cacheNames);
	}
	
	/**
	 * 装饰缓存对象
	 * @param cache
	 * @return
	 */
	protected Cache decorateCache(Cache cache) {
		return cache;
	}
	
	/**
	 * 获取不存在的缓存对象
	 * @param name
	 * @return
	 */
	protected Cache getMissingCache(String name) {
		return null;
	}
}
