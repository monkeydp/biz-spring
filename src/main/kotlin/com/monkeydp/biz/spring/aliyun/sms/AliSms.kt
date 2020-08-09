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
    override fun innerSend(params: SendParams) {
        config.apply {
            val profile: IClientProfile = DefaultProfile.getProfile(
                    regionid,
                    accessKeyId,
                    accessKeySecret
            )
            DefaultProfile.addEndpoint(
                    regionid,
                    product,
                    domain
            )
            val request = SendSmsRequest()
                    .apply {
                        setSysMethod(POST)
                        phoneNumbers = params.address.toString()
                        signName = signName
                        templateCode = templateCode
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
        val regionid: String,
        val product: String,
        val domain: String,
        val accessKeyId: String,
        val accessKeySecret: String,
        val signName: String,
        val templateCode: String
)

class SmsSendParams
