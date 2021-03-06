package com.monkeydp.biz.spring.regex

import com.monkeydp.biz.spring.regex.RegExp.EMAIL
import com.monkeydp.biz.spring.regex.RegExp.MOBILE
import java.util.regex.Pattern

/**
 * @author iPotato-Work
 * @date 2020/7/29
 */
object RegExp {
    const val MOBILE = "^1[3456789]\\d{9}\$"
    const val EMAIL = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+\$"
    val p = Pattern.compile("d[1-9][0-9]*_.*")

    fun az09(configInit: (Az09Config.() -> Unit)? = null): String =
            Az09Config().run {
                configInit?.invoke(this)

                val builder = StringBuilder()

                if (!allowedPureNumber)
                    builder.append("(?![0-9]+\$)")

                if (!allowedPureAlphabet)
                    builder.append("(?![a-zA-Z]+\$)")

                builder.append("[a-zA-Z0-9$allowedSymbol]")

                val range = "{$min,}"
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

    // 允许的符号
    var allowedSymbol = ""
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
