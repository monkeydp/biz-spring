package com.monkeydp.biz.spring.ext.spring.web.client

import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

/**
 * @author iPotato-Work
 * @date 2020/7/11
 */
inline fun <reified R> RestTemplate.get(
        url: String,
        data: Any? = null,
        headers: HttpHeaders = HttpHeaders(),
) =
        request<R>(url, HttpMethod.GET, data, headers)

inline fun <reified R> RestTemplate.post(
        url: String,
        data: Any? = null,
        headers: HttpHeaders = HttpHeaders(),
) =
        request<R>(url, HttpMethod.POST, data)

inline fun <reified R> RestTemplate.request(
        url: String,
        method: HttpMethod,
        data: Any? = null,
        headers: HttpHeaders = HttpHeaders(),
): ResponseEntity<R> {
    val typeRef = object : ParameterizedTypeReference<R>() {}
    val entity = if (data != null) HttpEntity(data, headers) else null
    return exchange(url, method, entity, typeRef)
}
