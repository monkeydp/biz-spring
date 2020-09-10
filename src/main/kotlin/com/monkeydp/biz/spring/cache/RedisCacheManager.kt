package com.monkeydp.biz.spring.cache

import com.monkeydp.biz.spring.ext.spring.redis.expire
import com.monkeydp.tools.ext.jackson.convertValue
import com.monkeydp.tools.ext.kotlin.singleton
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import java.io.Serializable
import java.time.Duration
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates
import kotlin.reflect.KClass

/**
 * @author iPotato-Work
 * @date 2020/6/2
 */
class RedisCacheManager : BaseCacheManager() {

    @set:Autowired
    private var redisTemplate: RedisTemplate<String, Serializable> by Delegates.singleton()

    override fun set(key: String, value: Serializable) {
        redisTemplate.opsForValue().set(key, value)
    }

    override fun set(key: String, value: Serializable, timeout: Long, timeUnit: TimeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit)
    }

    override fun set(key: String, value: Serializable, timeout: Duration) {
        redisTemplate.opsForValue().set(key, value, timeout)
    }

    override fun <T : Serializable> get(key: String, kClass: KClass<T>): T =
            getOrNull(key, kClass)!!

    @Suppress("UNCHECKED_CAST")
    override fun <T : Serializable> getOrNull(key: String, kClass: KClass<T>): T? =
            redisTemplate.opsForValue()
                    .get(key)
                    ?.convertValue(kClass)

    override fun exist(key: String): Boolean =
            redisTemplate.hasKey(key)

    override fun delete(key: String) {
        redisTemplate.delete(key)
    }

    override fun expire(key: String, timeout: Long, timeUnit: TimeUnit) {
        redisTemplate.expire(key, timeout, timeUnit)
    }

    override fun expire(key: String, timeout: Duration) {
        redisTemplate.expire(key, timeout)
    }
}
