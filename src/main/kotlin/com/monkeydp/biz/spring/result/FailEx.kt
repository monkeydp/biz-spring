package com.monkeydp.biz.spring.result

import com.monkeydp.tools.ext.kotlin.toDataString

/**
 * @author iPotato-Work
 * @date 2020/7/11
 */
class FailEx(val result: FailResult) : RuntimeException() {
    override fun toString() =
            toDataString()
}

fun failerr(result: FailResult): Nothing =
        throw FailEx(result)
