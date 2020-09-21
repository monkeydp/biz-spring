package com.monkeydp.biz.spring.result

import com.monkeydp.biz.spring.ex.BizEx
import com.monkeydp.biz.spring.result.CommonErrorInfo.ARGUMENT_ILLEGAL

/**
 * @author iPotato-Work
 * @date 2020/9/21
 */
class ArgsIllegalEx(
        val errors: List<ValidError>
) : BizEx(ARGUMENT_ILLEGAL) {
    constructor(error: ValidError) : this(listOf(error))
}

fun verror(error: ValidError): Nothing {
    throw ArgsIllegalEx(error)
}

fun verror(errors: List<ValidError>): Nothing {
    throw ArgsIllegalEx(errors)
}
