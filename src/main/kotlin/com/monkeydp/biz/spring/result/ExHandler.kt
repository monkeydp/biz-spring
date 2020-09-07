package com.monkeydp.biz.spring.result

import com.monkeydp.biz.spring.auth.AccountNotExistEx
import com.monkeydp.biz.spring.auth.AuthFailEx
import com.monkeydp.biz.spring.auth.PwdIncorrectEx
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

    fun handle(throwable: Throwable) {
        when (throwable) {
            is FailEx -> handle(throwable)
            is MethodArgumentNotValidException -> handle(throwable)
            is BindException -> handle(throwable)
            is BizEx -> handle(throwable)
            is InnerException -> handle(throwable)
            is Exception -> handle(throwable)
            else -> throw throwable
        }
    }

    fun handle(ex: AccountNotExistEx): Nothing =
            throw AuthFailEx(ex)

    fun handle(ex: PwdIncorrectEx): Nothing =
            throw AuthFailEx(ex)

    fun handle(ex: FailEx) =
            ex.result

    fun handle(ex: MethodArgumentNotValidException) =
            ArgsIllegalFailedResult(ex, ex.bindingResult)

    fun handle(ex: BindException) =
            ArgsIllegalFailedResult(ex, ex.bindingResult)

    fun handle(ex: BizEx) =
            BizFailedResult(ex)

    fun handle(ex: InnerException) =
            InnerFailedResult(ex)

    fun handle(ex: Exception) =
            FailResult(ex, INNER_ERROR)
}
