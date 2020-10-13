package com.monkeydp.biz.spring.crud

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.monkeydp.biz.spring.crud.PagingQuery.Companion.DEFAULT_CURRENT_PAGE
import com.monkeydp.biz.spring.crud.PagingQuery.Companion.DEFAULT_PAGE_SIZE
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.springframework.data.jpa.domain.Specification

/**
 * @author iPotato
 * @date 2020/4/27
 */
@ApiModel("分页查询表单")
interface PagingQueryForm {
    @get:ApiModelProperty("当前页码", example = DEFAULT_CURRENT_PAGE.toString())
    val currentPage: Int

    @get:ApiModelProperty("每页记录数", example = DEFAULT_PAGE_SIZE.toString())
    val pageSize: Int
    fun toPagingQuery(): PagingQuery

    companion object {
        operator fun invoke(
                currentPage: Int = DEFAULT_CURRENT_PAGE,
                pageSize: Int = DEFAULT_PAGE_SIZE
        ): PagingQueryForm = PqForm(currentPage, pageSize)
    }
}

abstract class BasePqForm(
        @CurrentPageCstr
        @get:ApiModelProperty("当前页码", example = DEFAULT_CURRENT_PAGE.toString())
        override val currentPage: Int = DEFAULT_CURRENT_PAGE,

        @PageSizeCstr
        @get:ApiModelProperty("每页记录数", example = DEFAULT_PAGE_SIZE.toString())
        override val pageSize: Int = DEFAULT_PAGE_SIZE
) : PagingQueryForm {
    override fun toPagingQuery() =
            PagingQuery(
                    currentPage = currentPage,
                    pageSize = pageSize
            )
}

class PqForm(
        currentPage: Int = DEFAULT_CURRENT_PAGE,
        pageSize: Int = DEFAULT_PAGE_SIZE
) : BasePqForm(currentPage, pageSize)

fun <E, ID> CrudService<E, ID>.findAll(form: PagingQueryForm): Paging<E> =
        findAll(form.toPagingQuery())

fun <E, ID> CrudService<E, ID>.findAll(spec: Specification<E>, form: PagingQueryForm): Paging<E> =
        findAll(spec, form.toPagingQuery())
