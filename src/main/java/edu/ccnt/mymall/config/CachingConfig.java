package edu.ccnt.mymall.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by LXY on 2017/10/9.
 */
@Configuration
//@EnableCaching
public class CachingConfig {

//    @Bean
//    public CacheManager cacheManager(RedisTemplate<?,?> redisTemplate) {
//        CacheManager cacheManager = new RedisCacheManager(redisTemplate);
//        return cacheManager;
//    }


//    @Bean
//    public JedisConnectionFactory redisConnectionFactory(){
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//        jedisConnectionFactory.afterPropertiesSet();
//        return jedisConnectionFactory;
//    }
//
//    @Bean
//    public RedisTemplate<String,String> redisTemplate(RedisConnectionFactory redisCF){
//        RedisTemplate<String,String> redisTemplate = new RedisTemplate<String,String>();
//        redisTemplate.setConnectionFactory(redisCF);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
}
