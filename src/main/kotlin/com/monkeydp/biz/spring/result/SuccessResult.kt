package com.monkeydp.biz.spring.result

import com.fasterxml.jackson.annotation.JsonPropertyOrder

/**
 * @author iPotato-Work
 * @date 2020/5/15
 */
interface SuccessResult<T> : Result {
    override val code: String get() = SUCCESS_CODE
    val data: T

    companion object {
        const val SUCCESS_CODE = "200"
        operator fun <T> invoke(data: T): SuccessResult<T> =
                SuccessResultImpl(data)
    }
}

@JsonPropertyOrder("code", "data")
abstract class AbstractSuccessResult<T>(
        override val data: T
) : SuccessResult<T>, AbstractResult() {
    protected override val showProps =
            listOf(SuccessResult<*>::code, SuccessResult<*>::data)
}

private class SuccessResultImpl<T>(data: T) : AbstractSuccessResult<T>(data)
