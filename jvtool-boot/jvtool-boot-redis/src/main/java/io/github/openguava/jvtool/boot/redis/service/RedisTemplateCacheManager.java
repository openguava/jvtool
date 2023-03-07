package io.github.openguava.jvtool.boot.redis.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import io.github.openguava.jvtool.boot.redis.RedisTemplateCache;
import io.github.openguava.jvtool.lang.cache.AbstractCacheManager;
import io.github.openguava.jvtool.lang.cache.Cache;
import io.github.openguava.jvtool.lang.util.CacheUtils;
import io.github.openguava.jvtool.lang.util.LogUtils;

@Component
public class RedisTemplateCacheManager extends AbstractCacheManager implements InitializingBean {

	@Autowired
	public RedisTemplate<String, Object> redisTemplate;

	@Override
	protected Cache getMissingCache(String name) {
		return new RedisTemplateCache(name, this.redisTemplate);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		LogUtils.info(RedisTemplateCacheManager.class, "启用 redis 缓存");
		CacheUtils.setDefaultCacheManager(this);
	}
}
