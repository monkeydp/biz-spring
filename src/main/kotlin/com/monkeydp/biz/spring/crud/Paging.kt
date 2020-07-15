package com.monkeydp.biz.spring.crud

import com.monkeydp.tools.ext.jackson.JsonFlatten
import com.monkeydp.tools.ext.jackson.JsonFlatten.Times.TWO
import com.monkeydp.tools.ext.swagger.ApiFixedPosition
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.springframework.data.domain.Page

@ApiFixedPosition
@ApiModel("一页记录")
data class Paging<T>(
        @ApiModelProperty("当前页码", example = "1")
        val currentPage: Int,

        @ApiModelProperty("每页记录数", example = "10")
        val pageSize: Int,

        @ApiModelProperty("记录总数", example = "13")
        val total: Long,

        @ApiModelProperty("总页数", example = "2")
        val pageCount: Int,

        @ApiModelProperty("当前页记录行数", example = "2")
        val rows: Int,

        /**
         * Json 序列化前未知
         */
        @ApiModelProperty("当前页记录列数", example = "2")
        var columns: Int = -1,

        @JsonFlatten(TWO)
        @ApiModelProperty("记录数据")
        val content: List<T>
) {
    constructor(page: Page<T>) : this(
            currentPage = page.number + 1,
            pageSize = page.size,
            total = page.totalElements,
            pageCount = page.totalPages,
            rows = page.content.size,
            content = page.content
    )

    fun <T> clone(content: List<T>): Paging<T> =
            Paging<T>(
                    currentPage = currentPage,
                    pageSize = pageSize,
                    total = total,
                    pageCount = pageCount,
                    rows = rows,
                    content = content
            )

    fun <R> map(transform: (T) -> R): Paging<R> =
            clone(
                    content.map(transform)
            )
}