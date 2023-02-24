package io.github.openguava.jvtool.lang.cache;

import java.util.Collection;

public interface CacheManager<K, V> {

	Cache<K, V> getCache(String name);
	
	Collection<String> getCacheNames();
}
