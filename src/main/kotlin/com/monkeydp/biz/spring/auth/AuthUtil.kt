package com.monkeydp.biz.spring.auth

import java.lang.reflect.Method

/**
 * @author iPotato-Work
 * @date 2020/6/2
 */
object AuthUtil {

    private val authAnnotClass = Auth::class.java
    private val authIgnoreAnnotClass = AuthIgnore::class.java

    fun needAuth(controllerMethod: Method): Boolean =
            controllerMethod.run {
                if (isAnnotationPresent(authIgnoreAnnotClass)) return@run false
                declaringClass.isAnnotationPresent(authAnnotClass)
                        || isAnnotationPresent(authAnnotClass)
            }
}