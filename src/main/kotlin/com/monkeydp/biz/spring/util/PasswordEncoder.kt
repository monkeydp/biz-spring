package com.monkeydp.biz.spring.util

import org.apache.commons.codec.digest.DigestUtils

/**
 * @author iPotato-Work
 * @date 2020/6/1
 */
interface PasswordEncoder {
    fun encode(password: String): String

    companion object {
        operator fun invoke(): PasswordEncoder = StdPasswordEncoder()
    }
}

private class StdPasswordEncoder : PasswordEncoder {
    override fun encode(password: String): String =
            DigestUtils.sha1Hex(password)
}

