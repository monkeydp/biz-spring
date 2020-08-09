package com.monkeydp.biz.spring.aliyun.sms

import com.monkeydp.biz.spring.result.ResultInfo

/**
 * @author iPotato-Work
 * @date 2020/8/9
 */
class SmsErrInfo(
        override val code: String,
        override val msgPattern: String
) : ResultInfo
