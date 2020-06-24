package com.monkeydp.biz.spring.global

import com.monkeydp.tools.ext.javax.validation.CarrierConstraint
import javax.validation.Constraint
import javax.validation.Payload
import javax.validation.constraints.NotBlank
import kotlin.reflect.KClass

/**
 * @author iPotato-Work
 * @date 2020/6/24
 */
@NotBlank
@Target(AnnotationTarget.FIELD)
@CarrierConstraint("{id}")
@Constraint(validatedBy = [])
annotation class IdCstr(
        val message: String = "",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)