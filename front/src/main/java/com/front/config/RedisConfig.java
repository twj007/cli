package com.front.config;

import com.front.component.SecurityRedisUserCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/16
 **/
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);

        //ObjectMapper mapper = new ObjectMapper();
        //mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        //serializer.setObjectMapper(mapper);
        template.setValueSerializer(serializer);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
    /**
     * Spring缓存管理器配置
     *
     * @param redisTemplate
     * @return
     */
    @Bean("redisCacheManager")
    public RedisCacheManager redisCacheManager(RedisTemplate redisTemplate) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisTemplate.getConnectionFactory());
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()));
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }
//    /**
//     * shiro缓存管理器的配置
//     *
//     * @param redisCacheManager
//     * @return
//     */
//    @Bean("shiroRedisManager")
//    public ShiroRedisManager shiroRedisCacheManager(RedisCacheManager redisCacheManager) {
//        ShiroRedisManager cacheManager = new ShiroRedisManager();
//        cacheManager.setCacheManager(redisCacheManager);
//        //name是key的前缀，可以设置任何值，无影响，可以设置带项目特色的值
//        return cacheManager;
//    }

    @Bean("securityRedisManager")
    public SecurityRedisUserCacheManager securityRedisUserCacheManager(RedisCacheManager redisCacheManager){
        SecurityRedisUserCacheManager cacheManager = new SecurityRedisUserCacheManager();
        cacheManager.setCacheManager(redisCacheManager);
        return cacheManager;
    }


}
