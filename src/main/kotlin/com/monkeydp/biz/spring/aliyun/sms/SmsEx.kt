package com.monkeydp.biz.spring.aliyun.sms

import com.aliyuncs.exceptions.ClientException
import com.monkeydp.biz.spring.ex.BizEx

/**
 * @author iPotato-Work
 * @date 2020/8/9
 */
class SmsSendEx(
        errInfo: SmsErrInfo,
        cause: ClientException? = null
) : BizEx(errInfo, cause)
