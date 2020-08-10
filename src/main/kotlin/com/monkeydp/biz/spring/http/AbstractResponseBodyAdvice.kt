package com.monkeydp.biz.spring.http

import com.monkeydp.biz.spring.ex.BizEx
import com.monkeydp.biz.spring.result.ExHandler
import com.monkeydp.biz.spring.result.FailEx
import com.monkeydp.biz.spring.result.Result
import com.monkeydp.biz.spring.result.SuccessResult
import com.monkeydp.tools.exception.inner.InnerException
import com.monkeydp.tools.ext.kotlin.toJson
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

/**
 * @author iPotato-Work
 * @date 2020/5/15
 */
abstract class AbstractResponseBodyAdvice : ResponseBodyAdvice<Any> {

    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>) = true

    override fun beforeBodyWrite(
            body: Any?,
            returnType: MethodParameter,
            selectedContentType: MediaType,
            selectedConverterType: Class<out HttpMessageConverter<*>>,
            request: ServerHttpRequest,
            response: ServerHttpResponse
    ): Any? =
            if (body is Result) body
            else
                SuccessResult(data = body).run {
                    if (body is String)
                        this.toJson() as Any
                    else this
                }

    @ResponseBody
    @ExceptionHandler(Exception::class)
    open fun handle(ex: Exception) =
            ExHandler.handle(ex)

    @ResponseBody
    @ExceptionHandler(InnerException::class)
    open fun handle(ex: InnerException) =
            ExHandler.handle(ex)

    @ResponseBody
    @ExceptionHandler(BizEx::class)
    open fun handle(ex: BizEx) =
            ExHandler.handle(ex)

    @ResponseBody
    @ExceptionHandler(BindException::class)
    open fun handle(ex: BindException) =
            ExHandler.handle(ex)

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException::class)
    open fun handle(ex: MethodArgumentNotValidException) =
            ExHandler.handle(ex)

    @ResponseBody
    @ExceptionHandler(FailEx::class)
    open fun handle(ex: FailEx) =
            ExHandler.handle(ex)
}
