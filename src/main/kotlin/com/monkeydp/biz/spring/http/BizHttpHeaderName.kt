package com.monkeydp.biz.spring.http

import com.monkeydp.biz.spring.http.BizHttpHeaderName.FLATTEN
import com.monkeydp.biz.spring.http.BizHttpHeaderName.WITH_KEYS
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

private fun ServerHttpRequest.headerOrNull(headerName: String) =
        (headers[headerName] as? List<String>)?.first()

private val ServerHttpRequest.withKeys: Boolean
    get() =
        headerOrNull(WITH_KEYS)?.toBoolean() ?: DEFAULT_WITH_KEYS

private val ServerHttpRequest.removeAllKeys
    get() = !withKeys

private val ServerHttpRequest.flatten: Boolean
    get() =
        headerOrNull(FLATTEN)?.toBoolean() ?: DEFAULT_FLATTEN

val ServerHttpRequest.respDataCfg
    get() =
        ResponseDataConfig(
                flatten = flatten,
                removeAllKeys = removeAllKeys
        )


private val HttpServletRequest.withKeys: Boolean
    get() =
        getHeader(WITH_KEYS)?.toBoolean() ?: DEFAULT_WITH_KEYS

private val HttpServletRequest.removeAllKeys: Boolean
    get() = !withKeys

private val HttpServletRequest.flatten: Boolean
    get() =
        getHeader(FLATTEN)?.toBoolean() ?: DEFAULT_WITH_KEYS

val HttpServletRequest.respDataCfg
    get() =
        ResponseDataConfig(
                flatten = flatten,
                removeAllKeys = removeAllKeys
        )
