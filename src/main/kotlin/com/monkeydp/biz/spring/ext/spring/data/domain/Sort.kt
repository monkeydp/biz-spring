package com.monkeydp.biz.spring.ext.spring.data.domain

import com.monkeydp.tools.ext.kotlin.camelToSnake
import org.springframework.data.domain.Sort
import kotlin.reflect.KProperty1

/**
 * @author iPotato-Work
 * @date 2021/1/27
 */
fun KProperty1<*, *>.toSort(options: (ToSortOptions.() -> Unit)? = null) =
        ToSortOptions(options).run {
            val prop = StringBuilder().apply {
                name.run(::append)
            }.toString()
            Sort.by(prop)
        }

class ToSortOptions(
) {
    companion object {
        operator fun invoke(init: (ToSortOptions.() -> Unit)? = null) =
                ToSortOptions().apply {
                    init?.invoke(this)
                }
    }
}
