package com.monkeydp.biz.spring.ext.spring.jackson

import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.monkeydp.tools.config.ToolsKodeinModuleContainer
import com.monkeydp.tools.ext.kotlin.singleton
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import kotlin.properties.Delegates

/**
 * @author iPotato
 * @date 2020/4/25
 */
abstract class BaseJacksonConfig : InitializingBean {

    @set:Autowired
    private var builder: Jackson2ObjectMapperBuilder by Delegates.singleton()

    @Bean
    open fun objectMapper(): ObjectMapper =
            builder.build<ObjectMapper>()
                    .registerKotlinModule()
                    .registerModule(Hibernate5Module())
                    .setSerializationInclusion(NON_NULL)
                    .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .apply { config() }

    protected open fun ObjectMapper.config(){
        // empty
    }

    @Bean
    open fun mappingJackson2HttpMessageConverter(objectMapper: ObjectMapper) =
            MappingJackson2HttpMessageConverter(objectMapper)

    override fun afterPropertiesSet() {
        ToolsKodeinModuleContainer.addModule("jacksonModule") {
            bind<ObjectMapper>(overrides = true) with singleton { objectMapper() }
        }
    }
}
