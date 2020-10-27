package com.monkeydp.biz.spring.crud

/**
 * @author iPotato-Work
 * @date 2020/10/27
 */
interface Condition{
    val wheres: List<Where>
}

interface Where {
    val property: String
    val operator: Operator
    val value: String

    enum class Operator {
        EQ,     // Equal to
        GT,     // Greater than
        LT,     // Less than
        GTE,    // Greater than or equal to
        LTE,    // Less than or equal to
    }
}
