package com.monkeydp.biz.spring.result

interface ResultInfo {
    val code: String
    val msgPattern: String
    fun buildMessage(args: Map<String, Any> = emptyMap()): String =
            com.ibm.icu.text.MessageFormat(msgPattern)
                    .format(args)
}
