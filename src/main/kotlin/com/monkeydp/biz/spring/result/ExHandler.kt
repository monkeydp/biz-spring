package com.monkeydp.biz.spring.result

import com.monkeydp.biz.spring.ex.BizEx
import com.monkeydp.biz.spring.result.CommonInfo.INNER_ERROR
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException
import org.springframework.validation.BindException

/**
 * @author iPotato-Work
 * @date 2020/6/11
 */
object ExHandler {
    fun handle(ex: Exception) =
            DefaultFailedResult(ex, INNER_ERROR)

    fun handle(ex: BizEx) =
            BizFailedResult(ex)

    fun handle(ex: BindException) =
            ArgsIllegalFailedResult(ex, ex.bindingResult)

    fun handle(ex: MethodArgumentNotValidException) =
            ArgsIllegalFailedResult(ex, ex.bindingResult)
}