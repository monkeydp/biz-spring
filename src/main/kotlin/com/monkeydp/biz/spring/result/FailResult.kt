package com.monkeydp.biz.spring.result

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.monkeydp.biz.spring.ex.BizEx
import com.monkeydp.biz.spring.result.CommonInfo.ARGUMENT_ILLEGAL
import com.monkeydp.tools.exception.ierror
import com.monkeydp.tools.ext.logger.error
import com.monkeydp.tools.ext.logger.getLogger
import com.monkeydp.tools.ext.logger.info
import com.monkeydp.tools.ext.logger.log
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError

/**
 * @author iPotato-Work
 * @date 2020/5/15
 */
@JsonPropertyOrder("code", "msg")
interface FailResult : Result {
    override val code: String
    var msg: String
}

abstract class AbstractFailResult(
        override val code: String,
        override var msg: String
) : FailResult, AbstractResult() {
    constructor(ex: Exception, resultInfo: ResultInfo<*>) :
            this(resultInfo.code.toString(), ex.message ?: "")
}

class DefaultFailedResult(
        ex: Exception,
        resultInfo: ResultInfo<*>
) : AbstractFailResult(ex, resultInfo) {

    companion object {
        private val logger = getLogger()
    }

    init {
        logger.error(ex)
    }
}

class BusinessFailedResult(
        bizEx: BizEx
) : AbstractFailResult(bizEx, bizEx.info) {
    companion object {
        private val logger = getLogger()
    }

    init {
        logger.log(bizEx.logLevel, bizEx) { "业务异常" }
    }
}

class ArgsIllegalFailedResult(
        ex: Exception,
        bindingResult: BindingResult
) : AbstractFailResult(ex, ARGUMENT_ILLEGAL) {
    companion object {
        private val logger = getLogger()
    }

    init {
        logger.info(ex) { ARGUMENT_ILLEGAL.msgPattern }
    }

    val validErrors: List<ValidError> =
            bindingResult.run {
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
            }
}


class ValidError(
        val message: String,
        val cstrName: String,
        val objName: String,
        val propName: String,
        val illegalValue: String
)