package com.monkeydp.biz.spring.ext.spring.web.client

import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

/**
 * @author iPotato-Work
 * @date 2020/7/11
 */
inline fun <reified R> RestTemplate.get(url: String, data: Any? = null) =
        request<R>(url, HttpMethod.GET, data)

inline fun <reified R> RestTemplate.post(url: String, data: Any? = null) =
        request<R>(url, HttpMethod.POST, data)

inline fun <reified R> RestTemplate.request(url: String, method: HttpMethod, data: Any? = null): ResponseEntity<R> {
    val typeRef = object : ParameterizedTypeReference<R>() {}
    val entity = if (data != null) HttpEntity(data) else null
    return exchange(url, method, entity, typeRef)
}
