package com.monkeydp.biz.spring.cache

import com.monkeydp.tools.constant.Symbol.DOT
import com.monkeydp.tools.constant.Symbol.UNDERSCORE
import java.io.Serializable
import java.time.Duration
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

/**
 * @author iPotato-Work
 * @date 2020/6/2
 */
class CacheManagerShadow(
        private val cacheManager: CacheManager,
        private val prefix: String
) : BaseCacheManager() {
    override fun set(key: String, value: Serializable) =
            cacheManager.set(key.run(::fullKey), value)

    override fun set(key: String, value: Serializable, timeout: Long, timeUnit: TimeUnit) =
            cacheManager.set(key.run(::fullKey), value, timeout, timeUnit)

    override fun set(key: String, value: Serializable, timeout: Duration) =
            cacheManager.set(key.run(::fullKey), value, timeout)

    override fun <T : Serializable> get(key: String, kClass: KClass<T>): T =
            cacheManager.get(key.run(::fullKey), kClass)

    override fun <T : Serializable> getOrNull(key: String, kClass: KClass<T>): T? =
            cacheManager.getOrNull(key.run(::fullKey), kClass)

    override fun exist(key: String): Boolean =
            cacheManager.exist(key.run(::fullKey))

    override fun delete(key: String) =
            cacheManager.delete(key.run(::fullKey))

    override fun expire(key: String, timeout: Long, timeUnit: TimeUnit) {
        cacheManager.expire(key.run(::fullKey), timeout, timeUnit)
    }

    override fun expire(key: String, timeout: Duration) {
        cacheManager.expire(key.run(::fullKey), timeout)
    }

    private fun fullKey(key: String) =
            "${prefix.toLowerCase().replace(UNDERSCORE, DOT)}.$key"
}

fun CacheManager.toShadow(prefix: String) =
        CacheManagerShadow(this, prefix)
