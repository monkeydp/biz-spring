package com.monkeydp.biz.spring.entity

import com.monkeydp.biz.spring.crud.HasCreatedAt
import com.monkeydp.biz.spring.crud.HasUpdatedAt
import com.monkeydp.tools.ext.jackson.toJson
import com.monkeydp.tools.ext.kotlin.toMemberPropValues
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.collection.internal.PersistentBag
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.MappedSuperclass

interface EntityAware : HasCreatedAt, HasUpdatedAt

interface Entity : EntityAware

@MappedSuperclass
abstract class AbstractEntity : Entity, Serializable {

    companion object {
        const val INVALID_ID = -1L
    }

    @field:CreationTimestamp
    @Column(nullable = false, updatable = false)
    override lateinit var createdAt: Date

    @field:UpdateTimestamp
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
