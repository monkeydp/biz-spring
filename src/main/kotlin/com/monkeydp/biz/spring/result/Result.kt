package com.monkeydp.biz.spring.result

import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.monkeydp.biz.spring.result.SuccessResult.Companion.SUCCESS_CODE
import com.monkeydp.tools.exception.ierror
import com.monkeydp.tools.ext.jackson.removeAllKeys
import com.monkeydp.tools.ext.kotlin.convertValue
import com.monkeydp.tools.ext.kotlin.singleton
import kotlin.properties.Delegates

/**
 * @author iPotato-Work
 * @date 2020/5/15
 */
interface Result {
    val code: String
    fun toArrayNodeWithoutAllKeys(): ArrayNode =
            convertValue<ObjectNode>().removeAllKeys()
}

abstract class AbstractResult : Result

class StdResult<T : Any>(
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
        get()  =
            FailResult(code, msg)
}