package com.monkeydp.biz.spring.crud

import com.monkeydp.biz.spring.entity.Entity
import org.springframework.data.domain.Sort

/**
 * @author iPotato-Work
 * @date 2020/8/11
 */
interface ListQuery {
    val sort: Sort

    companion object {
        val DEFAULT_SORT =
                Sort.by(Entity<*>::createdAt.name)
                        .descending()
    }
}
