package com.monkeydp.biz.spring.cache

import java.io.Serializable
import java.time.Duration
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

/**
 * @author iPotato-Work
 * @date 2020/6/2
 */
interface CacheManager {
    fun set(key: String, value: Serializable)
    fun set(key: String, value: Serializable, timeout: Long, timeUnit: TimeUnit)
    fun set(key: String, value: Serializable, timeout: Duration)
    fun <T : Serializable> get(key: String, kClass: KClass<T>): T
    fun <T : Serializable> getOrNull(key: String, kClass: KClass<T>): T?
    fun exist(key: String): Boolean
    fun delete(key: String)
    fun expire(key: String, timeout: Long, timeUnit: TimeUnit)
    fun expire(key: String, timeout: Duration)
}

abstract class BaseCacheManager : CacheManager


inline fun <reified T : Serializable> CacheManager.get(key: String): T =
        get(key, T::class)

inline fun <reified T : Serializable> CacheManager.getOrNull(key: String): T? =
        getOrNull(key, T::class)
