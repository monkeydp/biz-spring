package com.monkeydp.biz.spring.ext.javax.persistence

import javax.persistence.EntityExistsException
import javax.persistence.EntityManager

/**
 * @author iPotato-Work
 * @date 2020/5/29
 */
fun EntityManager.persistOrMerge(entity: Any) {
    try {
        persist(entity)
    } catch (ex: EntityExistsException) {
        merge(entity)
    }
}

fun EntityManager.persistAll(entities: Iterable<Any>) {
    entities.forEach(this::persist)
}

fun EntityManager.persistOrMergeAll(entities: Iterable<Any>) {
    entities.forEach(this::persistOrMerge)
}
