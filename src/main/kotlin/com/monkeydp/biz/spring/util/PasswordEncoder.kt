package com.monkeydp.biz.spring.util

import org.apache.commons.codec.digest.DigestUtils

/**
 * @author iPotato-Work
 * @date 2020/6/1
 */
interface PasswordEncoder {
    fun md5Hex(password: String): String
    fun sha1Hex(password: String): String
    fun sha256Hex(password: String): String

    companion object {
        operator fun invoke(): PasswordEncoder =
                object : PasswordEncoder {
                    override fun md5Hex(password: String) =
                            DigestUtils.md5Hex(password)

                    override fun sha1Hex(password: String) =
                            DigestUtils.sha1Hex(password)

                    override fun sha256Hex(password: String) =
                            DigestUtils.sha256Hex(password)
                }
    }
}

