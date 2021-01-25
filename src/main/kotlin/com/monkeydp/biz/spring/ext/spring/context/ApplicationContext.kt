package com.monkeydp.biz.spring.ext.spring.context

import org.springframework.context.ApplicationContext

/**
 * @author iPotato-Work
 * @date 2020/8/5
 */
inline fun <reified T> ApplicationContext.getBeanByName(beanName: String): T =
        getBean(beanName, T::class.java)

inline fun <reified T> ApplicationContext.getBeanByType(): T =
        getBean(T::class.java)

inline fun <reified T> ApplicationContext.getBeansByType() =
        getBeansOfType(T::class.java).toMap()
