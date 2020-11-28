package com.monkeydp.biz.spring.result

import com.monkeydp.biz.spring.result.BatchResult.*

/**
 * 批量操作返回
 *
 * @author iPotato-Work
 * @date 2020/11/28
 */
class BatchActionReturn<T>(
        val validErrorsMap: Map<T, Iterable<ValidError>> = emptyMap(),
        val successData: Collection<T> = emptyList(),
) {
    val result = when {
        validErrorsMap.size == 0 && successData.size == 0 -> NO_DATA
        validErrorsMap.size == 0 -> SUCCESS
        successData.size == 0 -> FAIL
        else -> PARTIAL_FAIL
    }
}

enum class BatchResult {
    NO_DATA,        // 无数据
    SUCCESS,        // 成功
    FAIL,           // 失败
    PARTIAL_FAIL    // 部分失败
}
