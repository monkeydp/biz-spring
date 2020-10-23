package com.monkeydp.biz.spring.http

import com.monkeydp.tools.ext.apache.commons.validator.checkValid
import com.monkeydp.tools.global.urlValidator
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod

/**
 * TODO some bug
 *
 * @author iPotato-Work
 * @date 2020/10/13
 */
class BizHttpShadow(
        val http: BizHttp,
        configInit: BizHttpShadowConfig.() -> Unit
) {
    private val config =
            BizHttpShadowConfig().apply {
                configInit(this)
            }

    inline fun <reified D : Any> get(
            url: String,
            data: Any? = null,
            headers: HttpHeaders = HttpHeaders(),
    ) =
            http.get<D>(fullUrl(url), data, fullHeaders(headers))

    inline fun <reified D : Any> post(
            url: String,
            data: Any? = null,
            headers: HttpHeaders = HttpHeaders(),
    ) =
            http.post<D>(fullUrl(url), data, fullHeaders(headers))

    inline fun <reified D : Any> request(
            url: String,
            method: HttpMethod,
            data: Any? = null,
            headers: HttpHeaders = HttpHeaders(),
    ) =
            http.request<D>(fullUrl(url), method, data, fullHeaders(headers))

    fun fullUrl(url: String) =
            StringBuilder().apply {
                if (urlValidator.isValid(url))
                    return url
                append(config.baseUrl)
                if (!url.startsWith("/"))
                    append("/")
                append(url)
            }.toString()

    fun fullHeaders(headers: HttpHeaders) =
            config.baseHeaders.apply {
                putAll(headers)
            }
}

class BizHttpShadowConfig {
    var baseUrl: String = ""
        set(value) {
            if (value.isNotBlank()) {
                urlValidator.checkValid(value)
            }
            var baseUrl = value
            while (baseUrl.endsWith("/"))
                baseUrl = baseUrl.removeSuffix("/")
            field = baseUrl
        }

    var baseHeaders: HttpHeaders = HttpHeaders()
}

fun BizHttp.toShadow(configInit: BizHttpShadowConfig.() -> Unit) =
        BizHttpShadow(
                http = this,
                configInit = configInit,
        )

