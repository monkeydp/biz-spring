package com.monkeydp.biz.spring.ext.swagger

import com.monkeydp.biz.spring.http.MyHttpHeaders.AUTO_LOGIN
import com.monkeydp.biz.spring.http.MyHttpHeaders.WITH_KEYS
import org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE
import org.springframework.http.HttpHeaders.AUTHORIZATION
import springfox.documentation.builders.ParameterBuilder
import springfox.documentation.schema.ModelRef

object SwaggerHttpHeaders {
    val withKeys =
            ParameterBuilder()
                    .name(WITH_KEYS)
                    .modelRef(ModelRef("boolean"))
                    .defaultValue("true")
                    .description("响应参数是否包含键名")
                    .parameterType("header")
                    .build()

    val autoLogin =
            ParameterBuilder()
                    .name(AUTHORIZATION)
                    .modelRef(ModelRef("string"))
                    .defaultValue(AUTO_LOGIN)
                    .description("用于验证用户代理身份的凭证")
                    .parameterType("header")
                    .build()

    val acceptLanguage =
            ParameterBuilder()
                    .name(ACCEPT_LANGUAGE)
                    .modelRef(ModelRef("string"))
                    .defaultValue("zh")
                    .description("语言")
                    .parameterType("header")
                    .build()
}