package com.monkeydp.biz.spring.crud

import com.monkeydp.biz.spring.global.IdCstr
import io.swagger.annotations.ApiModel

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