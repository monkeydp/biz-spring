package com.monkeydp.biz.spring.ext.spring.context

import org.springframework.context.ApplicationContext

/**
 * @author iPotato-Work
 * @date 2020/8/5
 */
inline fun <reified T> ApplicationContext.getBeanX(beanName: String): T =
        getBean(beanName, T::class.java)

inline fun <reified T> ApplicationContext.getBeans() =
        getBeansOfType(T::class.java).toMap()
