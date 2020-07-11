package com.monkeydp.biz.spring.result

import com.fasterxml.jackson.annotation.JsonPropertyOrder

/**
 * @author iPotato-Work
 * @date 2020/5/15
 */
@JsonPropertyOrder("code", "data")
interface SuccessResult<T> : Result {
    override val code: String get() = SUCCESS_CODE
    val data: T

    companion object {
        const val SUCCESS_CODE = "200"
        operator fun <T> invoke(data: T): SuccessResult<T> =
                StdSuccessResult(data)
    }
}

abstract class AbstractSuccessResult<T>(
        override val data: T
) : SuccessResult<T>, AbstractResult()

private class StdSuccessResult<T>(data: T) : AbstractSuccessResult<T>(data)