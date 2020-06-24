package com.monkeydp.biz.spring.ex

import com.monkeydp.biz.spring.result.ResultInfo
import com.monkeydp.tools.ext.logger.LogLevel.INFO

abstract class BizEx(
        val info: ResultInfo<*>,
        cause: Throwable? = null
) : RuntimeException(cause) {
    companion object {
        val DEFAULT_LOG_LEVEL = INFO
    }

    var logLevel = DEFAULT_LOG_LEVEL
    protected val args = mutableMapOf<String, Any>()
    override val message: String get() = info.buildMessage(args)
    override fun toString() = message
}