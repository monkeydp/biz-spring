package com.monkeydp.biz.spring.crud

import com.monkeydp.biz.spring.crud.ListQuery.Companion.DEFAULT_SORT
import org.springframework.data.domain.Sort

/**
 * @author iPotato-Work
 * @date 2020/8/11
 */
interface AllQuery : ListQuery {
    companion object {
        operator fun invoke(
                sort: Sort = DEFAULT_SORT
        ): AllQuery =
                object : AllQuery {
                    override val sort: Sort = sort
                }
    }
}
