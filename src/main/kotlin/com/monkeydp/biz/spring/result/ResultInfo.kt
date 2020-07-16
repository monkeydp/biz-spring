package com.monkeydp.biz.spring.result

import com.monkeydp.tools.enumx.Enumx

interface ResultInfo<E> : Enumx<E>
        where E : ResultInfo<E>, E : Enum<E> {
    val code: String
    val msgPattern: String
    fun buildMessage(args: Map<String, Any> = emptyMap()): String =
            com.ibm.icu.text.MessageFormat(msgPattern)
                    .format(args)
}