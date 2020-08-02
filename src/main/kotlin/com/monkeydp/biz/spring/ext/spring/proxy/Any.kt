@file:Suppress("UNCHECKED_CAST")

package com.monkeydp.biz.spring.ext.spring.proxy

import com.monkeydp.tools.exception.ierror
import org.springframework.aop.framework.AdvisedSupport
import org.springframework.aop.framework.AopProxy
import org.springframework.aop.support.AopUtils
import org.springframework.util.ClassUtils
import kotlin.reflect.KClass

/**
 * @author iPotato-Work
 * @date 2020/8/2
 */
@Suppress("UNCHECKED_CAST")
fun <T : Any> T.getUserClass(): KClass<T> =
        ClassUtils.getUserClass(this)
                .kotlin as KClass<T>

fun Any.isAopProxy() =
        AopUtils.isAopProxy(this)

fun Any.isJdkDynamicProxy() =
        AopUtils.isJdkDynamicProxy(this)

fun Any.isCglibProxy() =
        AopUtils.isCglibProxy(this)

fun <T : Any> T.getProxyTarget(): T =
        when {
            !isAopProxy() -> this
            isJdkDynamicProxy() -> getJdkDynamicProxyTargetObject()
            isCglibProxy() -> getCglibProxyTargetObject()
            else -> ierror("Unhandled proxy ${this.javaClass}")
        }

private fun <T : Any> T.getCglibProxyTargetObject(): T {
    val h = javaClass.getDeclaredField("CGLIB\$CALLBACK_0")
    h.setAccessible(true)
    val dynamicAdvisedInterceptor = h.get(this)
    val advised = dynamicAdvisedInterceptor.javaClass.getDeclaredField("advised")
    advised.setAccessible(true)
    advised.get(dynamicAdvisedInterceptor)
    return (advised.get(dynamicAdvisedInterceptor) as AdvisedSupport)
            .getTargetSource().getTarget()!! as T
}


private fun <T : Any> T.getJdkDynamicProxyTargetObject(): T {
    val h = javaClass.getSuperclass().getDeclaredField("h")
    h.setAccessible(true)
    val aopProxy = h.get(this) as AopProxy
    val advised = aopProxy.javaClass.getDeclaredField("advised")
    advised.setAccessible(true)
    return (advised.get(aopProxy) as AdvisedSupport)
            .getTargetSource().getTarget()!! as T
}
