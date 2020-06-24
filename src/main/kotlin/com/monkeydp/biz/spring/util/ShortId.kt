package com.monkeydp.biz.spring.util

import com.monkeydp.tools.util.RandomUtil

/**
 * Short unique id generator
 *
 * @author iPotato-Work
 * @date 2020/6/1
 */
interface ShortId {
    fun next(): String
}

/**
 * Generate by milliseconds and salt by default
 *
 * https://github.com/dylang/shortid
 */
private class StdShortId : ShortId {
    override fun next(): String {
        TODO("Not yet implemented")
    }
}

class ShortIdFake : ShortId {
    override fun next(): String = RandomUtil.randomAlphanumeric(8)
}