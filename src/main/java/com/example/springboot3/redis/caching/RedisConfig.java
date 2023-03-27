package com.example.springboot3.redis.caching;

import java.io.Serializable;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import jakarta.annotation.PostConstruct;
import redis.clients.jedis.Jedis;

@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableCaching
public class RedisConfig {
//	@Bean
//	public RedisCacheConfiguration cacheConfiguration() {
//	   return RedisCacheConfiguration.defaultCacheConfig()
//	   .entryTtl(Duration.ofMinutes(60)) // Changing the cache TTL
//	   .disableCachingNullValues()
//	   .serializeValuesWith(SerializationPair.fromSerializer(newGenericJackson2JsonRedisSerializer())); 
//	   //Defining serialization to retrieve data
//	}
	@Bean
	public RedisTemplate<String, Serializable> redisCacheTemplate(LettuceConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Serializable> template = new RedisTemplate<>();
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory factory) {
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
		RedisCacheConfiguration redisCacheConfiguration = config
				.serializeKeysWith(
						RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair
						.fromSerializer(new GenericJackson2JsonRedisSerializer()));
		RedisCacheManager redisCacheManager = RedisCacheManager.builder(factory).cacheDefaults(redisCacheConfiguration)
				.build();
		return redisCacheManager;
	}

	@PostConstruct
	public void clearCache() {
		System.out.println("In Clear Cache");
		Jedis jedis = new Jedis("localhost", 6379, 1000);
		jedis.flushAll();
		jedis.close();
	}
   
}
