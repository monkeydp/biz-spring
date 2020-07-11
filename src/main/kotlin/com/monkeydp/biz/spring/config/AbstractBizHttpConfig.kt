package com.monkeydp.biz.spring.config

import com.monkeydp.biz.spring.http.BizHttp
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

/**
 * @author iPotato-Work
 * @date 2020/7/11
 */
abstract class AbstractBizHttpConfig {
    @Bean
    open fun restTemplate() =
            RestTemplate()

    @Bean
    open fun bizHttp(restTemplate: RestTemplate) =
            BizHttp(restTemplate)
}