package com.monkeydp.biz.spring.result

/**
 * 批量操作返回
 *
 * @author iPotato-Work
 * @date 2020/11/28
 */
class BatchActionReturn<T>(
        val validErrorsMap: Map<T, Iterable<ValidError>>,
        val successData: Iterable<T> = emptyList(),
)
