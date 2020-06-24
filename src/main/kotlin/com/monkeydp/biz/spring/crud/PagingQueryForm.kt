package com.monkeydp.biz.spring.crud

import com.monkeydp.biz.spring.crud.PagingQuery.Companion.DEFAULT_CURRENT_PAGE
import com.monkeydp.biz.spring.crud.PagingQuery.Companion.DEFAULT_PAGE_SIZE
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * @author iPotato
 * @date 2020/4/27
 */
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

fun <E, ID> CrudService<E, ID>.findAll(pagingQueryForm: PagingQueryForm): Paging<E> =
        findAll(pagingQueryForm.toPagingQuery())