package com.monkeydp.biz.spring.crud

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.monkeydp.biz.spring.crud.PagingQuery.Companion.DEFAULT_CURRENT_PAGE
import com.monkeydp.biz.spring.crud.PagingQuery.Companion.DEFAULT_PAGE_SIZE
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.springframework.data.jpa.domain.Specification

/**
 * @author iPotato
 * @date 2020/4/27
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE, defaultImpl = StdPagingQueryForm::class)
@ApiModel("分页查询")
interface PagingQueryForm {
    val currentPage: Int
    val pageSize: Int
    fun toPagingQuery(): PagingQuery

    companion object {
        operator fun invoke(
                currentPage: Int,
                pageSize: Int
        ): PagingQueryForm = StdPagingQueryForm(currentPage, pageSize)
    }
}

abstract class BasePagingQueryForm(
        @CurrentPageCstr
        @ApiModelProperty("当前页码", example = DEFAULT_CURRENT_PAGE.toString())
        override val currentPage: Int = DEFAULT_CURRENT_PAGE,

        @PageSizeCstr
        @ApiModelProperty("每页记录数", example = DEFAULT_PAGE_SIZE.toString())
        override val pageSize: Int = DEFAULT_PAGE_SIZE
) : PagingQueryForm {
    override fun toPagingQuery() =
            PagingQuery(
                    currentPage = currentPage,
                    pageSize = pageSize
            )
}

class StdPagingQueryForm(
        currentPage: Int = DEFAULT_CURRENT_PAGE,
        pageSize: Int = DEFAULT_PAGE_SIZE
) : BasePagingQueryForm(currentPage, pageSize)

fun <E, ID> CrudService<E, ID>.findAll(form: PagingQueryForm): Paging<E> =
        findAll(form.toPagingQuery())

fun <E, ID> CrudService<E, ID>.findAll(spec: Specification<E>, form: PagingQueryForm): Paging<E> =
        findAll(spec, form.toPagingQuery())