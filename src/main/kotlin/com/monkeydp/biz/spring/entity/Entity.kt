package com.monkeydp.biz.spring.entity

import com.monkeydp.biz.spring.crud.HasCreatedAt
import com.monkeydp.biz.spring.crud.HasUpdatedAt
import com.monkeydp.tools.ext.jackson.toJson
import com.monkeydp.tools.ext.kotlin.camelToSnake
import com.monkeydp.tools.ext.kotlin.toMemberPropValues
import org.hibernate.collection.internal.PersistentBag
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass
import kotlin.reflect.KClass

interface EntityAware : HasCreatedAt, HasUpdatedAt

interface Entity : EntityAware

val <E : Entity> KClass<E>.tableName
    get() = simpleName!!.camelToSnake().toLowerCase()

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AbstractEntity : Entity, Serializable {

    companion object {
        const val INVALID_ID = -1L
    }

    @CreatedDate
    @Column(nullable = false, updatable = false)
    override lateinit var createdAt: Date

    @LastModifiedDate
    @Column(nullable = false)
    override lateinit var updatedAt: Date
}

/**
 * 完整信息
 *
 * 因为可能有 lazy load, 所需需要全部属性加载一次
 *
 * @see Transactional 如果有 lazy load, 记得使用 @Transactional
 */
fun <E : Entity> E.full(): E =
        run {
            toMemberPropValues().forEach {
                if (it is PersistentBag)
                    it.size
            }
            this
        }

/**
 * 是否和另一个实体相同 (包含关联实体)
 */
fun <E : Entity> E.isFullSameAs(another: E): Boolean =
        full().toJson() == another.full().toJson()
