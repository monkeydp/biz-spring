package com.monkeydp.biz.spring.result

import com.monkeydp.biz.spring.result.SuccessResult.Companion.SUCCESS_CODE
import com.monkeydp.tools.ext.kotlin.singleton
import com.monkeydp.tools.ext.kotlin.toDataString
import kotlin.properties.Delegates
import kotlin.reflect.KProperty1

/**
 * @author iPotato-Work
 * @date 2020/5/15
 */
interface Result {
    val code: String
}

abstract class AbstractResult : Result {
    protected open val showProps:List<KProperty1<out Result, *>> = listOf(Result::code)
    override fun toString() =
            toDataString(showProps = showProps)
}

class ResultImpl<T : Any>(
        override val code: String
) : AbstractResult() {

    // exist when success
    private var data: T by Delegates.singleton()

    // exist when fail
    private var msg: String by Delegates.singleton()

    val success =
            this.code == SUCCESS_CODE

    val successResult: SuccessResult<T>
        get() =
            SuccessResult.invoke(data)

    val failedResult: FailResult
        get() =
            FailResult(code, msg)
}
