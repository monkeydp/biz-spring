package com.monkeydp.biz.spring.ext.spring.data.jpa

import au.com.console.jpaspecificationdsl.get
import au.com.console.jpaspecificationdsl.isFalse
import au.com.console.jpaspecificationdsl.isTrue
import au.com.console.jpaspecificationdsl.where
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Path
import javax.persistence.criteria.Predicate
import kotlin.reflect.KProperty1

/**
 * @author iPotato-Work
 * @date 2020/12/25
 */
fun <T> KProperty1<T, Boolean?>.`is`(boolean: Boolean) =
        if (boolean) isTrue() else isFalse()

fun <T, R : Comparable<R>> KProperty1<T, R?>.compare(x: R, operator: String = "=") =
        spec {
            when (operator) {
                "=" -> equal(it, x)
                ">" -> greaterThan(it, x)
                "<" -> lessThan(it, x)
                "!=" -> notEqual(it, x)
                else -> error("Unhandle operator `$operator`!")
            }
        }

private fun <T, R> KProperty1<T, R?>.spec(makePredicate: CriteriaBuilder.(path: Path<R>) -> Predicate): Specification<T> =
        let { property -> where { root -> makePredicate(root.get(property)) } }
