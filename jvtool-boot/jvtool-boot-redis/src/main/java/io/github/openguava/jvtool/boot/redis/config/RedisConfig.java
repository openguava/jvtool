package io.github.openguava.jvtool.boot.redis.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import io.github.openguava.jvtool.boot.redis.RedisTemplateJsonSerializer;

@Configuration
@EnableCaching
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class RedisConfig extends CachingConfigurerSupport {

	/**
	 * 创建 RedisTemplate
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public RedisTemplate<String, Object> createRedisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);
		
		// serializer
		RedisTemplateJsonSerializer<Object> serializer = new RedisTemplateJsonSerializer<>(Object.class);
		
		// Key/Value
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(serializer);
		
		// HashKey/HashValue
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(serializer);
		
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
}
