package com.monkeydp.biz.spring.crud

import com.monkeydp.biz.spring.crud.Table.Companion.INVALID_COLUMNS
import com.monkeydp.tools.ext.jackson.JsonFlatten
import com.monkeydp.tools.ext.swagger.ApiFixedPosition

/**
 * @author iPotato-Work
 * @date 2020/6/4
 */
interface Table<T> {
    @JsonFlatten
    val content: Collection<T>
    val rows: Int
    val columns: Int

    companion object {
        const val INVALID_COLUMNS = -1
        operator fun <T> invoke(content: Collection<T>): Table<T> =
                TableImpl(content)
    }
}

@ApiFixedPosition
class TableImpl<T>(
        override val content: Collection<T>
) : Table<T> {
    override val rows = content.size
    override var columns: Int = INVALID_COLUMNS
}
