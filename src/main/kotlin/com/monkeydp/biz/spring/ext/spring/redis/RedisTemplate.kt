package com.monkeydp.biz.spring.ext.spring.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.TimeoutUtils
import java.time.Duration
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.TimeUnit.SECONDS

/**
 * @author iPotato-Work
 * @date 2020/9/9
 */
fun <K, V> RedisTemplate<K, V>.expire(key: K, timeout: Duration) {
    if (TimeoutUtils.hasMillis(timeout)) {
        expire(key, timeout.toMillis(), MILLISECONDS)
    } else {
        expire(key, timeout.getSeconds(), SECONDS)
    }
}
