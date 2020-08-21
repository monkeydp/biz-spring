package com.monkeydp.biz.spring.crud

import com.monkeydp.biz.spring.global.IdCstr
import io.swagger.annotations.ApiModel

@ApiModel("ID 查询表单")
data class IdQueryForm(
        @IdCstr
        override val id: String
) : HasId

inline fun <E, reified ID> CrudService<E, ID>.findById(form: IdQueryForm): E =
        findById(form.id())

inline fun <E, reified ID> CrudService<E, ID>.findByIdOrNull(form: IdQueryForm): E? =
        findByIdOrNull(form.id())

inline fun <E, reified ID> CrudService<E, ID>.deleteById(form: IdQueryForm) =
        deleteById(form.id())
