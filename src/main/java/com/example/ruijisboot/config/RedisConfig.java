package com.example.ruijisboot.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.*;

import java.text.SimpleDateFormat;
import java.time.Duration;

@Slf4j
@Configuration
@EnableCaching  // 使能Spring Cache缓存
public class RedisConfig extends CachingConfigurerSupport {

    // Key 过期时间: 1day = 86400s
    private Duration timeToLive = Duration.ofDays(1);

    // Spring Cache 配置类
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 创建String和JSON序列化对象，分别对key和value的数据进行类型转换
        RedisSerializer<String> strSerializer = new StringRedisSerializer();

        Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        jacksonSeial.setObjectMapper(mapper);

        ObjectMapper om = new ObjectMapper();
        // 注册JavaTimeModule来支持Java 8的日期和时间类型
        om.registerModule(new JavaTimeModule());
        // 配置ObjectMapper的其他属性，比如日期格式等
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        jacksonSeial.setObjectMapper(om);

        // 自定义缓存数据序列化方式和有效期限
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(1))   // 设置缓存有效期为1天
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(strSerializer)) // 使用strSerializer对key进行数据类型转换
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jacksonSeial)) // 使用jacksonSeial对value的数据类型进行转换
                .disableCachingNullValues();   // 对空数据不操作

        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(config).build();
        return cacheManager;
    }

    // Redis 配置类
    // 自定义的RedisTemplate的Bean名称必须为 redisTemplate。当方法名不为 redisTemplate时，可通过name显示指定bean名称，@Bean(name="redisTemplate")
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        // 注册JavaTimeModule来支持Java 8的日期和时间类型
        om.registerModule(new JavaTimeModule());
        // 配置ObjectMapper的其他属性，比如日期格式等
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        jackson2JsonRedisSerializer.setObjectMapper(om);

        // String 的序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // Hash的 key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // Hash的 value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }

    // key 采用String序列化器
    private RedisSerializer<String> getKeySerializer() {
        return new StringRedisSerializer();
    }

    // value 采用Json序列化器
    private RedisSerializer<Object> getValueSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    @Bean
    public ValueOperations<String, String> valueOperations(RedisTemplate<String, String> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }


}
