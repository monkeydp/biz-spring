package com.monkeydp.biz.spring.result

import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.monkeydp.biz.spring.result.SuccessResult.Companion.SUCCESS_CODE
import com.monkeydp.tools.ext.jackson.removeAllKeys
import com.monkeydp.tools.ext.kotlin.convertValue

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

class StdResult<T>(
        override val code: String,
        // exist when success
        val data: T? = null,
        // exist when fail
        val msg: String? = null
) : AbstractResult() {
    val success =
            this.code == SUCCESS_CODE

//    val successResult: SuccessResult<T>
//        get() = SuccessResult.invoke(data)
}