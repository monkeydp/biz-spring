package com.monkeydp.biz.spring

import com.monkeydp.tools.ext.javax.validation.CarrierConstraint
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import javax.validation.Constraint
import javax.validation.Payload
import javax.validation.constraints.Positive
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass


data class PagingQuery(
        @CurrentPageCstr
        val currentPage: Int = DEFAULT_CURRENT_PAGE,

        @PageSizeCstr
        val pageSize: Int = DEFAULT_PAGE_SIZE,

        val sort: Sort = DEFAULT_SORT
) {
    companion object {
        const val DEFAULT_CURRENT_PAGE = 1
        const val DEFAULT_PAGE_SIZE = 10
        final val DEFAULT_SORT =
                Sort.by(Entity::createdAt.name)
                        .descending()
    }

    val pageable: Pageable
        get() = PageRequest.of(currentPage - 1, pageSize, sort)
}

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