package com.monkeydp.biz.spring.util

import org.apache.commons.codec.digest.DigestUtils

/**
 * @author iPotato-Work
 * @date 2020/6/1
 */
interface PasswordEncoder {
    fun sha1Hex(password: String): String
    fun sha256Hex(password: String): String

    companion object {
        operator fun invoke(): PasswordEncoder = StdPasswordEncoder()
    }

    enum class Encryption {
        SHA1, SHA256
    }
}

private class StdPasswordEncoder : PasswordEncoder {
    override fun sha1Hex(password: String) =
            DigestUtils.sha1Hex(password)

    override fun sha256Hex(password: String) =
            DigestUtils.sha256Hex(password)
}

