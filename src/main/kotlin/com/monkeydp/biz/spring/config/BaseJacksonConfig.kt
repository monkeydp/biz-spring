package com.moonlight.sk.be.config

import com.fasterxml.jackson.databind.MapperFeature.DEFAULT_VIEW_INCLUSION
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module
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

    /**
     * Auto register module jackson-module-kotlin,
     * it's the module that adds support for serialization/deserialization of Kotlin classes and data classes.
     */
    @Bean
    open fun objectMapper(): ObjectMapper =
            builder.build<ObjectMapper>().configure(DEFAULT_VIEW_INCLUSION, true)
                    .registerModule(Hibernate5Module())

    @Bean
    open fun mappingJackson2HttpMessageConverter(objectMapper: ObjectMapper) =
            MappingJackson2HttpMessageConverter(objectMapper)

    override fun afterPropertiesSet() {
        ToolsKodeinModuleContainer.addModule("jacksonModule") {
            bind<ObjectMapper>(overrides = true) with singleton { objectMapper() }
        }
    }
}