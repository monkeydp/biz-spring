package com.monkeydp.biz.spring.ext.spring.redis

import com.monkeydp.tools.global.objectMapper
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.io.Serializable


/**
 * @author iPotato-Work
 * @date 2020/9/9
 */
abstract class BaseRedisConfig {
    @Bean
    fun redisTemplate(factory: RedisConnectionFactory) =
            RedisTemplate<String, Serializable>()
                    .apply {
                        setConnectionFactory(factory)
                        val strRedisSerializer = StringRedisSerializer()
                        val jackson2JsonRedisSerializer =
                                GenericJackson2JsonRedisSerializer(objectMapper.copy())
                        keySerializer = strRedisSerializer
                        valueSerializer = jackson2JsonRedisSerializer
                        hashKeySerializer = strRedisSerializer
                        hashValueSerializer = jackson2JsonRedisSerializer
                    }
}
