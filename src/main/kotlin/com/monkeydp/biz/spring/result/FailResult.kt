package com.monkeydp.biz.spring.result

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.monkeydp.biz.spring.ex.BizEx
import com.monkeydp.biz.spring.result.CommonInfo.ARGUMENT_ILLEGAL
import com.monkeydp.biz.spring.result.CommonInfo.INNER_ERROR
import com.monkeydp.tools.exception.ierror
import com.monkeydp.tools.exception.inner.InnerException
import com.monkeydp.tools.ext.logger.debug
import com.monkeydp.tools.ext.logger.error
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
    var msg: String

    companion object {
        operator fun invoke(code: String, msg: String): FailResult =
                UnknownFailedResult(code = code, msg = msg)

        operator fun invoke(code: Any, msg: String): FailResult =
                invoke(code.toString(), msg)

        operator fun invoke(
                ex: Exception,
                resultInfo: ResultInfo
        ): FailResult =
                UnknownFailedResult(ex = ex, resultInfo = resultInfo)
    }
}

@JsonPropertyOrder("code", "msg")
abstract class AbstractFailResult(
        override val code: String,
        override var msg: String
) : FailResult, AbstractResult() {
    constructor(resultInfo: ResultInfo) : this(resultInfo.code, resultInfo.msgPattern)
}

private class UnknownFailedResult(
        code: String,
        msg: String
) : AbstractFailResult(code, msg) {
    companion object {
        private val logger = getLogger()
    }

    constructor (
            ex: Exception,
            resultInfo: ResultInfo
    ) : this(resultInfo.code, resultInfo.msgPattern) {
        logger.error(ex)
    }
}

class InnerFailedResult(
        ex: InnerException
) : AbstractFailResult(INNER_ERROR.code, ex.msg) {
    companion object {
        private val logger = getLogger()
    }

    init {
        ex.config.apply {
            when {
                hidestack -> logger.log(logLevel, "${ex.msg} - ${ex.stackTrace.first()}")
                else -> logger.log(logLevel, ex)
            }
        }
    }
}

class BizFailedResult(
        ex: BizEx
) : AbstractFailResult(ex.info.code, ex.message) {
    companion object {
        private val logger = getLogger()
    }

    init {
        logger.log(ex.logLevel, "${ex.info.code} - ${ex.message}")
    }
}

class ArgsIllegalFailedResult(
        ex: Exception,
        bindingResult: BindingResult?
) : AbstractFailResult(ARGUMENT_ILLEGAL) {

    companion object {
        private val logger = getLogger()
    }

    init {
        logger.debug(ex)
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
