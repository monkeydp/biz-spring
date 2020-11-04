package com.monkeydp.biz.spring.crud

import org.springframework.data.domain.Sort

/**
 * @author iPotato-Work
 * @date 2020/8/11
 */
interface ListQuery : Query {
    val sort: Sort

    companion object {
        val DEFAULT_SORT =
                Sort.by("id")
                        .descending()
    }
}
