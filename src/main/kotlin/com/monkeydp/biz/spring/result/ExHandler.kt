package com.monkeydp.biz.spring.result

import com.monkeydp.biz.spring.ex.BizEx
import com.monkeydp.biz.spring.result.CommonInfo.INNER_ERROR
import com.monkeydp.tools.exception.inner.InnerException
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException

/**
 * @author iPotato-Work
 * @date 2020/6/11
 */
object ExHandler {
    fun handle(ex: Exception) =
            FailResult(ex, INNER_ERROR)

    fun handle(ex: InnerException) =
            InnerFailedResult(ex)

    fun handle(ex: BizEx) =
            BizFailedResult(ex)

    fun handle(ex: BindException) =
            ArgsIllegalFailedResult(ex, ex.bindingResult)

    fun handle(ex: MethodArgumentNotValidException) =
            ArgsIllegalFailedResult(ex, ex.bindingResult)

    fun handle(ex: FailEx) =
            ex.result
}
