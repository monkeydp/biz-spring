package com.monkeydp.biz.spring

import com.monkeydp.biz.spring.PagingQuery.Companion.DEFAULT_CURRENT_PAGE
import com.monkeydp.biz.spring.PagingQuery.Companion.DEFAULT_PAGE_SIZE
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("分页查询")
data class PagingQueryForm(
    @CurrentPageCstr
    @ApiModelProperty("当前页码", example = DEFAULT_CURRENT_PAGE.toString())
    val currentPage: Int = DEFAULT_CURRENT_PAGE,

    @PageSizeCstr
    @ApiModelProperty("每页记录数", example = DEFAULT_PAGE_SIZE.toString())
    val pageSize: Int = DEFAULT_PAGE_SIZE
) {
    fun toPagingQuery() =
        PagingQuery(
            currentPage = currentPage,
            pageSize = pageSize
        )
}

fun <E : Any, ID : Any> CrudService<E, ID>.findAll(form: PagingQueryForm): Paging<E> =
    findAll(form.toPagingQuery())