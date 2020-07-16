package com.monkeydp.biz.spring.util

import com.monkeydp.tools.global.faker

/**
 * @author iPotato-Work
 * @date 2020/6/5
 */
interface SecretKey {
    fun next(): String

    companion object {
        operator fun invoke(): SecretKey = StdSecretKey()
    }
}

private class StdSecretKey : SecretKey {
    override fun next(): String {
        TODO("Not yet implemented")
    }
}

class SecretKeyFake : SecretKey {
    override fun next(): String = faker.crypto().sha1()
}