package com.monkeydp.biz.spring.ext.spring.web.client

import com.monkeydp.tools.ext.kotlin.toMemberPropMapX
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpMethod.GET
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

/**
 * @author iPotato-Work
 * @date 2020/7/11
 */
inline fun <reified R> RestTemplate.get(
        url: String,
        data: Any? = null,
        headers: HttpHeaders = HttpHeaders(),
) =
        request<R>(url, GET, data, headers)

inline fun <reified R> RestTemplate.post(
        url: String,
        data: Any? = null,
        headers: HttpHeaders = HttpHeaders(),
) =
        request<R>(url, HttpMethod.POST, data, headers)

inline fun <reified R> RestTemplate.request(
        url: String,
        method: HttpMethod,
        data: Any? = null,
        headers: HttpHeaders = HttpHeaders(),
): ResponseEntity<R> {
    val typeRef = object : ParameterizedTypeReference<R>() {}
    val entity =
            when (method) {
                GET -> HttpEntity(headers)
                else -> HttpEntity(data, headers)
            }
    println(finalUrl(url, method, data))
    return exchange(finalUrl(url, method, data), method, entity, typeRef)
}

fun finalUrl(
        url: String,
        method: HttpMethod,
        data: Any? = null,
) =
        if (data == null) url
        else when (method) {
            GET -> {
                UriComponentsBuilder
                        .fromHttpUrl(url)
                        .apply {
                            data.toMemberPropMapX<String, Any>()
                                    .forEach {
                                        queryParam(it.key, it.value)
                                    }
                        }.build().toUriString()
            }
            else -> url
        }
