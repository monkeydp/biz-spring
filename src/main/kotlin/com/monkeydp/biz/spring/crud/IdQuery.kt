package com.monkeydp.biz.spring.crud

import com.monkeydp.tools.ext.javax.validation.CarrierConstraint
import io.swagger.annotations.ApiModel
import javax.validation.Constraint
import javax.validation.Payload
import javax.validation.constraints.NotBlank
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass

@ApiModel("ID 查询")
data class IdQuery(
        @IdCstr
        override val id: String
) : HasId

inline fun <E, reified ID> CrudService<E, ID>.findById(idQuery: IdQuery): E =
        findById(idQuery.id())

inline fun <E, reified ID> CrudService<E, ID>.findByIdOrNull(idQuery: IdQuery): E? =
        findByIdOrNull(idQuery.id())

inline fun <E, reified ID> CrudService<E, ID>.deleteById(idQuery: IdQuery) =
        deleteById(idQuery.id())

@NotBlank
@Target(FIELD)
@CarrierConstraint("{id}")
@Constraint(validatedBy = [])
annotation class IdCstr(
        val message: String = "",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)