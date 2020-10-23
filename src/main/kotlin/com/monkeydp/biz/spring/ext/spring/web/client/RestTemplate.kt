package com.monkeydp.biz.spring.ext.spring.web.client

import com.monkeydp.tools.ext.kotlin.toMemberPropMapX
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpMethod.GET
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import org.springframework.web.util.UriComponentsBuilder

/**
 * @author iPotato-Work
 * @date 2020/7/11
 */
inline fun <reified T> RestTemplate.get(
        url: String,
        data: Any? = null,
        headers: HttpHeaders = HttpHeaders(),
) =
        request<T>(url, GET, data, headers)

inline fun <reified T> RestTemplate.post(
        url: String,
        data: Any? = null,
        headers: HttpHeaders = HttpHeaders(),
) =
        request<T>(url, HttpMethod.POST, data, headers)

inline fun <reified T> RestTemplate.request(
        url: String,
        method: HttpMethod,
        data: Any? = null,
        headers: HttpHeaders = HttpHeaders(),
): ResponseEntity<T> {
    val entity =
            when (method) {
                GET -> HttpEntity(headers)
                else -> HttpEntity(data, headers)
            }
    return exchange(finalUrl(url, method, data), method, entity)
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
