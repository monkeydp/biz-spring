package com.monkeydp.biz.spring.http

import org.springframework.http.HttpMethod

/**
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

    private val baseUrl =
            config.baseUrl.removeSuffix("/")

    inline fun <reified D : Any> get(url: String, data: Any? = null) =
            http.get<D>(fullUrl(url), data)

    inline fun <reified D : Any> post(url: String, data: Any? = null) =
            http.post<D>(fullUrl(url), data)

    inline fun <reified D : Any> request(url: String, method: HttpMethod, data: Any? = null) =
            http.request<D>(fullUrl(url), method, data)

    fun fullUrl(url: String) =
            StringBuilder().apply {
                append(baseUrl)
                if (!url.startsWith("/"))
                    append("/")
                append(url)
            }.toString()
}

class BizHttpShadowConfig {
    var baseUrl: String = ""
}

fun BizHttp.toShadow(configInit: BizHttpShadowConfig.() -> Unit) =
        BizHttpShadow(
                http = this,
                configInit = configInit,
        )

