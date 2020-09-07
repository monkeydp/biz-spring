package com.monkeydp.biz.spring.http

import com.monkeydp.biz.spring.http.DefaultBizHttpHeader.DEFAULT_FLATTEN
import com.monkeydp.biz.spring.http.DefaultBizHttpHeader.DEFAULT_WITH_KEYS
import org.springframework.http.server.ServerHttpRequest
import javax.servlet.http.HttpServletRequest

/**
 * @author iPotato-Work
 * @date 2020/6/2
 */
object BizHttpHeaderName {
    const val AUTO_LOGIN = "autologin"
    const val WITH_KEYS = "withKeys"
    const val FLATTEN = "flatten"
}

private object DefaultBizHttpHeader {
    const val DEFAULT_WITH_KEYS = true
    const val DEFAULT_FLATTEN = false
}

fun ServerHttpRequest.headerOrNull(headerName: String) =
        (headers[headerName] as? List<String>)?.first()

val ServerHttpRequest.withKeys: Boolean
    get() =
        headerOrNull(BizHttpHeaderName.WITH_KEYS)?.toBoolean() ?: DEFAULT_WITH_KEYS

val ServerHttpRequest.flatten: Boolean
    get() =
        headerOrNull(BizHttpHeaderName.FLATTEN)?.toBoolean() ?: DEFAULT_FLATTEN

val ServerHttpRequest.removeAllKeys
    get() = !withKeys

val HttpServletRequest.withKeys: Boolean
    get() =
        getHeader(BizHttpHeaderName.WITH_KEYS)?.toBoolean() ?: DEFAULT_WITH_KEYS

val HttpServletRequest.removeAllKeys: Boolean
    get() = !withKeys
