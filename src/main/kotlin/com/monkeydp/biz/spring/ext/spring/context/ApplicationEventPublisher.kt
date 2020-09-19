package com.monkeydp.biz.spring.ext.spring.context

import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationEventPublisher

/**
 * @author iPotato-Work
 * @date 2020/9/19
 */
fun ApplicationEventPublisher.publishEvents(events: List<ApplicationEvent>) {
    events.forEach { publishEvent(it) }
}
