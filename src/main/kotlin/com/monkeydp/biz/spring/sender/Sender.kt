package com.monkeydp.biz.spring.sender

import com.monkeydp.biz.spring.regex.isEmail
import com.monkeydp.biz.spring.regex.isMobile
import com.monkeydp.tools.exception.ierror
import com.monkeydp.tools.module.json.Jsonable

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

interface SendParams {
    val content: CharSequence
    val address: CharSequence

    companion object {
        operator fun invoke(
                content: CharSequence,
                address: CharSequence
        ): SendParams =
                object : SendParams {
                    override val content = content
                    override val address = address
                }

        operator fun invoke(
                content: Jsonable,
                address: CharSequence
        ): SendParams =
                invoke(
                        content = content.toJson(),
                        address = address
                )
    }
}

/**
 * ShortMessageService
 */
interface Sms : Sender

abstract class BaseSms : BaseSender(), Sms {
    override fun checkAddress(address: CharSequence) {
        if (!address.isMobile())
            ierror("发送地址有误, $address 不是手机号")
    }
}

interface EmailService : Sender

abstract class BaseEmailService : BaseSender(), EmailService {
    override fun checkAddress(address: CharSequence) {
        if (!address.isEmail())
            ierror("发送地址有误, $address 不是邮箱")
    }
}
