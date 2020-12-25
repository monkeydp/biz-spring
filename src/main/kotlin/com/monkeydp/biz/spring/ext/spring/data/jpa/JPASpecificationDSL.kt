package com.monkeydp.biz.spring.ext.spring.data.jpa

import au.com.console.jpaspecificationdsl.isFalse
import au.com.console.jpaspecificationdsl.isTrue
import kotlin.reflect.KProperty1

/**
 * @author iPotato-Work
 * @date 2020/12/25
 */
fun <T> KProperty1<T, Boolean?>.`is`(boolean: Boolean) =
        if (boolean) isTrue() else isFalse()
