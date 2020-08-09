package com.monkeydp.biz.spring.aliyun.sms

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse

/**
 * @author iPotato-Work
 * @date 2020/8/9
 */
val SendSmsResponse.success: Boolean
    get() = code?.equals("OK") ?: false
