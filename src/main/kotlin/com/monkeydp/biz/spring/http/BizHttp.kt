package com.monkeydp.biz.spring.http

import com.monkeydp.biz.spring.ext.spring.web.client.request
import com.monkeydp.biz.spring.result.ResultImpl
import com.monkeydp.biz.spring.result.failerr
import org.springframework.http.HttpMethod
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.POST
import org.springframework.web.client.RestTemplate

/**
 * @author iPotato-Work
 * @date 2020/7/11
 */
class BizHttp(
        val restTemplate: RestTemplate
) {
    inline fun <reified D : Any> get(url: String, data: Any? = null) =
            request<D>(url, GET, data)

    inline fun <reified D : Any> post(url: String, data: Any? = null) =
            request<D>(url, POST, data)

    inline fun <reified D : Any> request(url: String, method: HttpMethod, data: Any? = null): D {
        val resp = restTemplate.request<ResultImpl<D>>(url, method, data)
        val result = resp.body!!
        if (result.success)
            return result.successResult.data
        failerr(result.failedResult)
    }
}
