package com.monkeydp.biz.spring.regex

import com.monkeydp.biz.spring.regex.RegExp.EMAIL
import com.monkeydp.biz.spring.regex.RegExp.MOBILE

/**
 * @author iPotato-Work
 * @date 2020/7/29
 */
object RegExp {
    const val MOBILE = "^1[3456789]\\d{9}\$"
    const val EMAIL = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+\$"

    fun az09(configInit: (Az09Config.() -> Unit)? = null): String =
            Az09Config().run {
                configInit?.invoke(this)
                val notAllowedPureNumber = "(?![0-9]+\$)"
                val notAllowedPureAlphabet = "(?![a-zA-Z]+\$)"
                val mixNumberAndAlphabet = "[0-9a-zA-Z]"
                val range = "{$min,}"
                val builder = StringBuilder()
                if (!allowedPureNumber)
                    builder.append(notAllowedPureNumber)
                if (!allowedPureAlphabet)
                    builder.append(notAllowedPureAlphabet)
                builder.append(mixNumberAndAlphabet)
                builder.append(range)
                builder.toString()
            }
}

class Az09Config {
    // 允许纯数字
    var allowedPureNumber = true

    // 允许纯字母
    var allowedPureAlphabet = true

    // 最小几位
    var min = 0
}

fun CharSequence.matches(pattern: String) =
        Regex(pattern).matches(this)

/**
 * 判断账号是否为手机号
 */
fun CharSequence.isMobile() =
        matches(MOBILE)

/**
 * 判断账号是否为邮箱
 */
fun CharSequence.isEmail() =
        matches(EMAIL)
