package io.github.openguava.jvtool.boot.redis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import io.github.openguava.jvtool.boot.redis.config.RedisConfig;
import io.github.openguava.jvtool.boot.redis.service.RedisTemplateCacheManager;

/**
 * spring boot redis 注解
 * 
 * @author openguava
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ RedisConfig.class, RedisTemplateCacheManager.class })
public @interface EnableBootRedis {

}
