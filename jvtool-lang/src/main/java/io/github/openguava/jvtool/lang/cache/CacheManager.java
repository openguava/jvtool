package io.github.openguava.jvtool.lang.cache;

import java.util.Collection;

public interface CacheManager {

	Cache getCache(String name);
	
	Collection<String> getCacheNames();
}
