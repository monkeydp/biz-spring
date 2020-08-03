package com.monkeydp.biz.spring.util

/**
 * Short unique id generator
 *
 * @author iPotato-Work
 * @date 2020/6/1
 */
interface ShortId {
    fun next(): String

    companion object {
        operator fun invoke(): ShortId =
                StdShortId()
    }
}

/**
 * Generate by milliseconds and salt by default
 *
 * https://github.com/dylang/shortid
 */
private class StdShortId : ShortId {
    override fun next() =
            me.nimavat.shortid.ShortId.generate()
}
