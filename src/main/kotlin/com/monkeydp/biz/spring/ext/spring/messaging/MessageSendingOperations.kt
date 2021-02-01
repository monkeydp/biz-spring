package com.monkeydp.biz.spring.ext.spring.messaging

import org.springframework.messaging.core.MessageSendingOperations

/**
 * @author iPotato-Work
 * @date 2021/2/2
 */
fun <D : Any> MessageSendingOperations<D>.send(destination: D, payload: Any? = null) {
    convertAndSend(destination, payload ?: "")
}
