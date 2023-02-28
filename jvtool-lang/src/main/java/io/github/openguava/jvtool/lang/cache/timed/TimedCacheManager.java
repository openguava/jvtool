package io.github.openguava.jvtool.lang.cache.timed;

import io.github.openguava.jvtool.lang.cache.AbstractCacheManager;
import io.github.openguava.jvtool.lang.cache.Cache;

public class TimedCacheManager extends AbstractCacheManager {

	private static final TimedCacheManager instance = new TimedCacheManager();
	
	public static TimedCacheManager getInstance() {
		return instance;
	}
	
	@Override
	protected Cache getMissingCache(String name) {
		return new TimedCache(name);
	}
	
	public TimedCacheManager() {
		super();
	}
	
	public static void main(String[] args) {
		new TimedCacheManager().getCache("");
	}
}
