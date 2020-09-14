package com.monkeydp.biz.spring.result

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.monkeydp.biz.spring.ex.BizEx
import com.monkeydp.biz.spring.ex.BizEx.Companion.DEFAULT_LOG_LEVEL
import com.monkeydp.biz.spring.result.CommonInfo.ARGUMENT_ILLEGAL
import com.monkeydp.biz.spring.result.CommonInfo.INNER_ERROR
import com.monkeydp.tools.exception.ierror
import com.monkeydp.tools.exception.inner.InnerEx
import com.monkeydp.tools.ext.logger.*
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError

/**
 * @author iPotato-Work
 * @date 2020/5/15
 */
interface FailResult : Result {
    override val code: String
    val msg: String

    companion object {
        operator fun invoke(code: String, msg: String): FailResult =
                FailedResultImpl(code = code, msg = msg)

        operator fun invoke(code: Any, msg: String): FailResult =
                invoke(code.toString(), msg)

        operator fun invoke(
                resultInfo: ResultInfo,
                cause: Throwable
        ): FailResult =
                FailedResultImpl(resultInfo = resultInfo, cause = cause)
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
            resultInfo: ResultInfo,
            cause: Throwable? = null
    ) : this(resultInfo.code, resultInfo.msgPattern) {
        if (cause != null)
            logger.error(cause)
    }

    protected override val showProps =
            listOf(FailResult::code, FailResult::msg)
}

private class FailedResultImpl : AbstractFailResult {
    companion object {
        private val logger = getLogger()
    }

    constructor(code: String, msg: String) : super(code, msg)
    constructor (
            resultInfo: ResultInfo,
            cause: Throwable? = null
    ) : super(resultInfo, cause)
}

open class InnerFailedResult(
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

    fun hideMsg() {
        logger.info("隐藏内部错误信息, 原失败结果: $this")
        this.msg = "内部错误"
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
                        append("- ${it.message}")
                }
            }.toString()
}

class ArgsIllegalFailedResult(
        cause: Exception,
        bindingResult: BindingResult?
) : AbstractFailResult(ARGUMENT_ILLEGAL) {

    companion object {
        private val logger = getLogger()
    }

    init {
        logger.debug(cause)
    }

    val validErrors: List<ValidError> =
            bindingResult?.run {
                allErrors
                        .filterIsInstance<FieldError>()
                        .apply { if (allErrors.size != size) ierror("存在未处理参数验证错误类型!") }
                        .map {
                            ValidError(
                                    message = it.defaultMessage ?: "<unknown>",
                                    objName = it.objectName,
                                    propName = it.field,
                                    cstrName = it.code ?: "<unknown>",
                                    illegalValue = it.rejectedValue?.toString() ?: "<unknown>"
                            )
                        }
            }.orEmpty()
}

class ValidError(
        val message: String,
        val cstrName: String,
        val objName: String,
        val propName: String,
        val illegalValue: String
)
