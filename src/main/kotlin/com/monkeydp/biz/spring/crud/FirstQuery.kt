package com.monkeydp.biz.spring.crud

import com.monkeydp.biz.spring.crud.ListQuery.Companion.DEFAULT_SORT
import org.springframework.data.domain.Sort

/**
 * @author iPotato-Work
 * @date 2020/8/11
 */
interface FirstQuery : AllQuery {
    companion object {
        operator fun invoke(
                sort: Sort = DEFAULT_SORT
        ): FirstQuery =
                object : FirstQuery {
                    override val sort: Sort = sort
                }
    }
}
