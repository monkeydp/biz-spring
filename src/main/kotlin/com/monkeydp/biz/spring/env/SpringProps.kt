package com.monkeydp.biz.spring.env

import com.monkeydp.biz.spring.env.Env.DEV
import com.monkeydp.biz.spring.env.Env.LOCAL
import com.monkeydp.biz.spring.env.Env.PROD
import com.monkeydp.biz.spring.env.Env.TEST

interface SpringProps {
    val profiles: Profiles
}

abstract class BaseSpringProps : SpringProps {
    override var profiles = Profiles()
}

interface Profiles {
    val active: String

    fun isLocal() = active == LOCAL
    fun isNotLocal() = !isLocal()

    fun isDev() = active == DEV
    fun isNotDev() = !isDev()
    fun lteDev() = isLocal() || isDev()
    fun gtDev() = !isLocal() && !isDev()

    fun isTest() = active == TEST
    fun isNotTest() = !isTest()
    fun lteTest() = lteDev() || isTest()

    fun isProd() = active == PROD
    fun isNotProd() = !isProd()

    companion object {
        operator fun invoke(): Profiles =
                object : Profiles {
                    override lateinit var active: String
                }
    }
}
