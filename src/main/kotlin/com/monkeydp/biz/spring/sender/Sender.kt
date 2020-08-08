package com.monkeydp.biz.spring.sender

import com.monkeydp.biz.spring.regex.isEmail
import com.monkeydp.biz.spring.regex.isMobile
import com.monkeydp.tools.exception.ierror

/**
 * @author iPotato-Work
 * @date 2020/8/8
 */
interface Sender {
    fun send(params: SendParams)
}

abstract class BaseSender : Sender {
    protected abstract fun checkAddress(address: CharSequence)
    protected abstract fun innerSend(params: SendParams)

    override fun send(params: SendParams) {
        checkAddress(params.address)
        innerSend(params)
    }
}

class SendParams(
        val content: CharSequence,
        val address: CharSequence
)

interface MobileSender : Sender

class MobileSenderImpl : BaseSender(), MobileSender {
    override fun checkAddress(address: CharSequence) {
        if (!address.isMobile())
            ierror("发送地址有误, $address 不是手机号")
    }

    override fun innerSend(params: SendParams) {
        TODO("Not yet implemented")
    }
}

interface EmailSender : Sender

class EmailSenderImpl : BaseSender(), EmailSender {
    override fun checkAddress(address: CharSequence) {
        if (!address.isEmail())
            ierror("发送地址有误, $address 不是邮箱")
    }

    override fun innerSend(params: SendParams) {
        TODO("Not yet implemented")
    }
}
