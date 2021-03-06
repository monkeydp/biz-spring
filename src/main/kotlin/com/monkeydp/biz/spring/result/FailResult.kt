package com.monkeydp.biz.spring.result

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.monkeydp.biz.spring.ex.BizEx
import com.monkeydp.biz.spring.ex.BizEx.Companion.DEFAULT_LOG_LEVEL
import com.monkeydp.biz.spring.result.CommonErrorInfo.ARGUMENT_ILLEGAL
import com.monkeydp.biz.spring.result.CommonErrorInfo.INNER_ERROR
import com.monkeydp.tools.exception.ierror
import com.monkeydp.tools.exception.inner.InnerEx
import com.monkeydp.tools.ext.logger.LogLevel
import com.monkeydp.tools.ext.logger.debug
import com.monkeydp.tools.ext.logger.getLogger
import com.monkeydp.tools.ext.logger.log
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError

/**
 * @author iPotato-Work
 * @date 2020/5/15
 */
interface FailResult : Result {
    override val code: String
    val msg: String

    fun hideMsg()

    companion object {
        operator fun invoke(code: String, msg: String): FailResult =
                FailedResultImpl(code = code, msg = msg)

        operator fun invoke(code: Any, msg: String): FailResult =
                invoke(code.toString(), msg)

        operator fun invoke(
                code: String,
                cause: Throwable
        ): FailResult =
                FailedResultImpl(code, cause = cause)
    }
}

@JsonPropertyOrder("code", "msg")
abstract class AbstractFailResult(
        override val code: String,
        override var msg: String
) : FailResult, AbstractResult() {

    companion object {
        private val logger = getLogger()
    }

    constructor(resultInfo: ResultInfo) : this(resultInfo.code, resultInfo.msgPattern)
    constructor (
            code: String,
            cause: Throwable
    ) : this(code, cause.message ?: "<无错误信息>") {
        cause.printStackTrace()
    }

    protected override val showProps =
            listOf(FailResult::code, FailResult::msg)

    override fun hideMsg() {
        logger.info("隐藏内部错误信息, 原失败结果: $this")
        this.msg = "内部错误"
    }
}

private class FailedResultImpl : AbstractFailResult {
    companion object {
        private val logger = getLogger()
    }

    constructor(code: String, msg: String) : super(code, msg)
    constructor (
            code: String,
            cause: Throwable
    ) : super(code, cause)
}

open class InnerFailResult(
        cause: InnerEx
) : AbstractFailResult(INNER_ERROR.code, cause.msg) {
    companion object {
        private val logger = getLogger()
    }

    init {
        cause.config.apply {
            when {
                hidestack -> logger.log(logLevel, cause.msg)
                else -> logger.log(logLevel, cause)
            }
        }
    }
}

open class BizFailedResult(
        code: String,
        msg: String,
        @JsonIgnore
        val cause: Throwable? = null,
        @JsonIgnore
        private val logLevel: LogLevel = DEFAULT_LOG_LEVEL
) : AbstractFailResult(code = code, msg = msg) {
    companion object {
        private val logger = getLogger()
    }

    constructor(cause: BizEx) : this(
            code = cause.info.code,
            msg = cause.message,
            cause = cause,
            logLevel = cause.logLevel
    )

    constructor(info: ResultInfo, cause: Throwable? = null, logLevel: LogLevel = DEFAULT_LOG_LEVEL) : this(
            code = info.code,
            msg = info.msgPattern,
            cause = cause,
            logLevel = logLevel
    )

    init {
        logger.log(logLevel, logMsg())
    }

    private fun logMsg() =
            StringBuilder().apply {
                append("${code} - ${msg}")
                cause?.let {
                    if (cause is InnerEx || cause is BizEx)
                        if (it.message != msg)
                            append(" - ${it.message}")
                }
            }.toString()
}

class ArgsIllegalResult(
        val validErrorsMap: Map<String, List<ValidError>>,
        cause: Exception
) : AbstractFailResult(ARGUMENT_ILLEGAL) {

    companion object {
        private val logger = getLogger()
    }

    constructor(ex: ArgsIllegalEx) : this(
            validErrorsMap = ex.errors.groupByPropName(),
            cause = ex
    )

    constructor(bindingResult: BindingResult?, cause: Exception) :
            this(
                    validErrorsMap = bindingResult?.run {
                        allErrors
                                .filterIsInstance<FieldError>()
                                .apply { if (allErrors.size != size) ierror("存在未处理参数验证错误类型!") }
                                .map { ValidError(it) }
                                .toList()
                                .groupByPropName()
                                .toMap()
                    }.orEmpty(),
                    cause = cause
            )

    init {
        logger.debug(cause)
    }
}

