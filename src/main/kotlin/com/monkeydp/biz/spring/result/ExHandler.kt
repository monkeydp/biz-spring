package com.monkeydp.biz.spring.result

import com.monkeydp.biz.spring.ex.BizEx
import com.monkeydp.biz.spring.result.CommonInfo.INNER_ERROR
import com.monkeydp.biz.spring.sms.SmsBusyEx
import com.monkeydp.biz.spring.sms.SmsSendEx
import com.monkeydp.tools.exception.ierror
import com.monkeydp.tools.exception.inner.InnerEx
import com.monkeydp.tools.ext.kotlin.getMethodOrNull
import com.monkeydp.tools.ext.kotlin.invokeMethod
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException

/**
 * @author iPotato-Work
 * @date 2020/6/11
 */
object ExHandler {

    fun handle(throwable: Throwable): FailResult {
        val handleMethod = ExHandler.getMethodOrNull("handle", throwable)
        if (handleMethod != null) return ExHandler.invokeMethod(handleMethod, throwable)
        return when (throwable) {
            is BizEx -> handle(throwable)
            is InnerEx -> handle(throwable)
            is Exception -> handle(throwable)
            else -> ierror("Unhandled throwable $throwable")
        }
    }

    fun handle(ex: SmsSendEx) =
            handle(SmsBusyEx(cause = ex))

    fun handle(ex: FailEx) =
            ex.result

    fun handle(ex: MethodArgumentNotValidException) =
            ArgsIllegalFailedResult(ex, ex.bindingResult)

    fun handle(ex: BindException) =
            ArgsIllegalFailedResult(ex, ex.bindingResult)

    fun handle(ex: BizEx) =
            BizFailedResult(ex)

    fun handle(ex: InnerEx) =
            InnerFailedResult(ex)

    fun handle(ex: Exception) =
            FailResult(code = INNER_ERROR.code, cause = ex)
}
