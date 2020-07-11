package com.monkeydp.biz.spring.result

/**
 * @author iPotato-Work
 * @date 2020/7/11
 */
class FailEx(val result: FailResult) : RuntimeException()

fun failerr(result: FailResult): Nothing =
        throw FailEx(result)