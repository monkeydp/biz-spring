package com.monkeydp.biz.spring.crud

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.monkeydp.biz.spring.crud.Table.Companion.INVALID_COLUMNS
import com.monkeydp.tools.ext.jackson.JsonFlatten
import com.monkeydp.tools.ext.swagger.ApiFixedPosition

/**
 * @author iPotato-Work
 * @date 2020/6/4
 */
interface Table<T> {
    val rows: Int
    val columns: Int

    @JsonFlatten
    val content: Collection<T>

    companion object {
        const val INVALID_COLUMNS = -1
        operator fun <T> invoke(content: Collection<T>): Table<T> =
                TableImpl(content)
    }
}

@ApiFixedPosition
@JsonPropertyOrder("rows", "columns", "content")
abstract class BaseTable<T>(
        content: Collection<T>
) : Table<T> {
    override val rows = content.size
    override var columns: Int = INVALID_COLUMNS
    override val content: Collection<T> = content
}

class TableImpl<T>(
        content: Collection<T>
) : BaseTable<T>(content)
