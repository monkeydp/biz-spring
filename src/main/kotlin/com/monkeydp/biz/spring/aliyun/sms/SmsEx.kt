package com.monkeydp.biz.spring.aliyun.sms

import com.aliyuncs.exceptions.ClientException
import com.monkeydp.biz.spring.ex.BizEx
import com.monkeydp.biz.spring.result.CommonInfo.SMS_BUSY

/**
 * @author iPotato-Work
 * @date 2020/8/9
 */
open class SmsSendEx(
        errInfo: SmsErrInfo,
        cause: ClientException? = null
) : BizEx(errInfo, cause)

class SmsBusyEx(
        cause: Throwable? = null
) : BizEx(SMS_BUSY, cause)
