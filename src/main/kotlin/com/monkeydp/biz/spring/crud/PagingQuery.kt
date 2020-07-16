package com.monkeydp.biz.spring.crud

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.monkeydp.biz.spring.entity.Entity
import com.monkeydp.tools.ext.javax.validation.CarrierConstraint
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import javax.validation.Constraint
import javax.validation.Payload
import javax.validation.constraints.Positive
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass

@JsonDeserialize(`as` = StdPagingQuery::class)
interface PagingQuery {
    val currentPage: Int
    val pageSize: Int
    val sort: Sort
    val pageable: Pageable

    companion object {
        const val DEFAULT_CURRENT_PAGE = 1
        const val DEFAULT_PAGE_SIZE = 10
        val DEFAULT_SORT =
                Sort.by(Entity<*>::createdAt.name)
                        .descending()

        operator fun invoke(
                currentPage: Int = DEFAULT_CURRENT_PAGE,
                pageSize: Int = DEFAULT_PAGE_SIZE,
                sort: Sort = DEFAULT_SORT
        ): PagingQuery =
                StdPagingQuery(
                        currentPage = currentPage,
                        pageSize = pageSize,
                        sort = sort
                )
    }
}

abstract class BasePagingQuery(
        @CurrentPageCstr
        override val currentPage: Int,

        @PageSizeCstr
        override val pageSize: Int,

        override val sort: Sort
) : PagingQuery {


    override val pageable: Pageable
        get() = PageRequest.of(currentPage - 1, pageSize, sort)
}

private class StdPagingQuery(
        currentPage: Int,
        pageSize: Int,
        sort: Sort
) : BasePagingQuery(currentPage, pageSize, sort)


@Positive
@Target(FIELD)
@CarrierConstraint("{currentPage}")
@Constraint(validatedBy = [])
annotation class CurrentPageCstr(
        val message: String = "",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)

@Positive
@Target(FIELD)
@CarrierConstraint("{pageSize}")
@Constraint(validatedBy = [])
annotation class PageSizeCstr(
        val message: String = "",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)