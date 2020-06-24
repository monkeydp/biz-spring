package com.monkeydp.biz.spring.ext.javax.persistence

import javax.persistence.EntityManager

/**
 * @author iPotato-Work
 * @date 2020/5/29
 */
fun EntityManager.persistAll(entities: Iterable<Any>) =
        entities.forEach(this::persist)