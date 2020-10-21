package com.monkeydp.biz.spring.http

import com.monkeydp.biz.spring.ext.spring.web.client.request
import com.monkeydp.biz.spring.result.Result
import com.monkeydp.biz.spring.result.ResultImpl
import com.monkeydp.biz.spring.result.failerr
import com.monkeydp.tools.ext.apache.commons.validator.checkValid
import com.monkeydp.tools.global.urlValidator
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.POST
import org.springframework.web.client.RestTemplate
import kotlin.reflect.KClass

/**
 * @author iPotato-Work
 * @date 2020/7/11
 */
interface BizHttp {
    fun <T : Any> get(
            url: String,
            data: Any? = null,
            headers: HttpHeaders = HttpHeaders(),
            returnType: KClass<T>,
    ): T

    fun <T : Any> post(
            url: String,
            data: Any? = null,
            headers: HttpHeaders = HttpHeaders(),
            returnType: KClass<T>,
    ): T

    fun <T : Any> request(
            url: String,
            method: HttpMethod,
            data: Any? = null,
            headers: HttpHeaders = HttpHeaders(),
            returnType: KClass<T>,
    ): T

    companion object {
        operator fun invoke(
                restTemplate: RestTemplate
        ): BizHttp =
                object : BaseBizHttp(restTemplate) {}
    }
}

inline fun <reified T : Any> BizHttp.get(
        url: String,
        data: Any? = null,
        headers: HttpHeaders = HttpHeaders(),
) =
        get(url, data, headers, T::class)

inline fun <reified T : Any> BizHttp.post(
        url: String,
        data: Any? = null,
        headers: HttpHeaders = HttpHeaders(),
) =
        post(url, data, headers, T::class)

inline fun <reified T : Any> BizHttp.request(
        url: String,
        method: HttpMethod,
        data: Any? = null,
        headers: HttpHeaders = HttpHeaders(),
) =
        request(url, method, data, headers, T::class)

abstract class BaseBizHttp(
        private val restTemplate: RestTemplate
) : BizHttp {
    override fun <T : Any> get(
            url: String,
            data: Any?,
            headers: HttpHeaders,
            returnType: KClass<T>,
    ) =
            request(url, GET, data, headers, returnType)

    override fun <T : Any> post(
            url: String,
            data: Any?,
            headers: HttpHeaders,
            returnType: KClass<T>,
    ) =
            request(url, POST, data, headers, returnType)

    override fun <T : Any> request(
            url: String,
            method: HttpMethod,
            data: Any?,
            headers: HttpHeaders,
            returnType: KClass<T>,
    ): T {
        urlValidator.checkValid(url)
        val resp =
                restTemplate.request<ResultImpl<T>>(url, method, data, headers)
        val result = resp.body!!
        if (result.success)
            return result.successResult.data
        failerr(result.failedResult)
    }
}
