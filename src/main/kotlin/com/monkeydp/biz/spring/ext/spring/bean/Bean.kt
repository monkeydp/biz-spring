package com.monkeydp.biz.spring.ext.spring.bean

import kotlin.reflect.KClass

/**
 * @author iPotato-Work
 * @date 2020/8/8
 */
val KClass<*>.beanName
    get() = simpleName!!.decapitalize()
