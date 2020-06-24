package com.monkeydp.biz.spring.crud

import com.monkeydp.tools.exception.ierror

/**
 * @author iPotato-Work
 * @date 2020/6/17
 */
interface HasId {
    val id: String
}

inline fun <reified ID> HasId.id(): ID =
        @Suppress("IMPLICIT_CAST_TO_ANY")
        when (ID::class) {
            String::class -> id
            Long::class -> id.toLong()
            Int::class -> id.toInt()
            else -> ierror("Unknown id type 「${ID::class}」")
        } as ID