package com.monkeydp.biz.spring.http

import com.fasterxml.jackson.databind.node.ObjectNode
import com.monkeydp.biz.spring.env.SpringProps
import com.monkeydp.biz.spring.result.ExHandler
import com.monkeydp.biz.spring.result.FailResult
import com.monkeydp.biz.spring.result.JsonSuccessResult
import com.monkeydp.biz.spring.result.Result
import com.monkeydp.tools.ext.jackson.removeAllKeys
import com.monkeydp.tools.ext.kotlin.convertValue
import com.monkeydp.tools.ext.kotlin.singleton
import com.monkeydp.tools.ext.kotlin.toJson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
import javax.servlet.http.HttpServletRequest
import kotlin.properties.Delegates

/**
 * @author iPotato-Work
 * @date 2020/5/15
 */
abstract class AbstractResponseBodyAdvice : ResponseBodyAdvice<Any> {

    @set:Autowired
    private var springProps: SpringProps by Delegates.singleton()

    @set:Autowired
    private var request: HttpServletRequest by Delegates.singleton()

    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>) = true

    override fun beforeBodyWrite(body: Any?, returnType: MethodParameter, selectedContentType: MediaType, selectedConverterType: Class<out HttpMessageConverter<*>>, request: ServerHttpRequest, response: ServerHttpResponse): Any? =
            if (body is Result) body
            else JsonSuccessResult(data = body, returnType = returnType)
                    .run {
                        val cfg = request.respDataCfg
                        if (body is String) toJson()
                        else toObjectNode()
                                .assignColumns()
                                .let {
                                    if (cfg.flatten) {
                                        it.flattenData()
                                    } else it
                                }.beforeRemoveAllKeys(this, cfg)
                                .let {
                                    if (cfg.removeAllKeys)
                                        it.removeAllKeys()
                                    else it
                                }
                    }

    protected open fun ObjectNode.beforeRemoveAllKeys(result: Result, cfg: ResponseDataConfig): ObjectNode = this

    @ResponseBody
    @ExceptionHandler(Throwable::class)
    open fun handle(throwable: Throwable): FailResult =
            ExHandler.handle(throwable)
}

private fun FailResult.handleKeys(removeAllKeys: Boolean): Any =
        if (removeAllKeys)
            convertValue<ObjectNode>().removeAllKeys()
        else this

class ResponseDataConfig(
        /**
         * 压缩数据
         */
        val flatten: Boolean = false,
        /**
         * 移除所有键
         */
        val removeAllKeys: Boolean = false
)
