package com.monkeydp.biz.spring.result

import com.fasterxml.jackson.annotation.JsonPropertyOrder

/**
 * @author iPotato-Work
 * @date 2020/5/15
 */
@JsonPropertyOrder("code", "data")
interface SuccessResult : Result {
    override val code: String get() = SUCCESS_CODE
    val data: Any?

    companion object {
        const val SUCCESS_CODE = "200"
        operator fun invoke(data: Any?): SuccessResult =
                StdSuccessResult(data)
    }
}

abstract class AbstractSuccessResult(
        override val data: Any?
) : SuccessResult, AbstractResult()

private class StdSuccessResult(data: Any?) : AbstractSuccessResult(data)