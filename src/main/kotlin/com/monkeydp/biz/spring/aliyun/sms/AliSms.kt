package com.monkeydp.biz.spring.aliyun.sms

import com.aliyuncs.DefaultAcsClient
import com.aliyuncs.IAcsClient
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest
import com.aliyuncs.exceptions.ClientException
import com.aliyuncs.http.MethodType.POST
import com.aliyuncs.profile.DefaultProfile
import com.aliyuncs.profile.IClientProfile
import com.monkeydp.biz.spring.sender.BaseSms
import com.monkeydp.biz.spring.sender.SendParams

/**
 * @author iPotato-Work
 * @date 2020/8/8
 */
class AliSms(
        private val config: SmsConfig
) : BaseSms() {

    constructor(configInit: SmsConfigBuilder.() -> Unit) : this(
            SmsConfig(SmsConfigBuilder().apply(configInit))
    )

    override fun innerSend(params: SendParams) {
        config.apply {
            val profile: IClientProfile = DefaultProfile.getProfile(
                    regionId,
                    accessKeyId,
                    accessKeySecret
            )
            DefaultProfile.addEndpoint(
                    regionId,
                    product,
                    domain
            )
            val request = SendSmsRequest()
                    .also {
                        it.setSysMethod(POST)
                        it.phoneNumbers = params.address.toString()
                        it.signName = signName
                        it.templateCode = templateCode
                    }

            val acsClient: IAcsClient = DefaultAcsClient(profile)
            val resp = try {
                acsClient.getAcsResponse(request)
            } catch (ex: ClientException) {
                throw SmsSendEx(
                        errInfo = SmsErrInfo(ex.errCode, ex.errMsg),
                        cause = ex
                )
            }
            if (!resp.success)
                throw SmsSendEx(
                        errInfo = SmsErrInfo(resp.code, resp.message)
                )
        }
    }
}

class SmsConfig(
        val regionId: String,
        val product: String,
        val domain: String,
        val accessKeyId: String,
        val accessKeySecret: String,
        val signName: String,
        val templateCode: String
) {
    constructor(builder: SmsConfigBuilder) : this(
            regionId = builder.regionId,
            product = builder.product,
            domain = builder.domain,
            accessKeyId = builder.accessKeyId,
            accessKeySecret = builder.accessKeySecret,
            signName = builder.signName,
            templateCode = builder.templateCode
    )
}

class SmsConfigBuilder {
    lateinit var regionId: String
    lateinit var product: String
    lateinit var domain: String
    lateinit var accessKeyId: String
    lateinit var accessKeySecret: String
    lateinit var signName: String
    lateinit var templateCode: String
}

class SmsSendParams
