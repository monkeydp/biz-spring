package com.monkeydp.biz.spring.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.monkeydp.tools.ext.kotlin.toJson
import com.monkeydp.tools.ext.kotlin.toMemberPropValues
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.collection.internal.PersistentBag
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.MappedSuperclass

interface Entity<E : Entity<E>> {
    var createdAt: Date
    var updatedAt: Date

    fun asChild(): E

    /**
     * 完整信息 (因为有 lazy load)
     */
    fun full(): E

    /**
     * 是否和另一个实体相同 (包含关联实体)
     */
    fun isFullSameAs(another: E): Boolean
}

@MappedSuperclass
abstract class AbstractEntity<E : AbstractEntity<E>> : Entity<E>, Serializable {

    companion object {
        const val INVALID_ID = -1L
    }

    @set:JsonIgnore
    @field:CreationTimestamp
    @Column(nullable = false, updatable = false)
    override lateinit var createdAt: Date

    @set:JsonIgnore
    @field:UpdateTimestamp
    @Column(nullable = false)
    override lateinit var updatedAt: Date

    @Suppress("UNCHECKED_CAST")
    override fun asChild(): E =
            this as E

    override fun isFullSameAs(another: E): Boolean =
            full().toJson() == another.full().toJson()

    /**
     * @see Transactional 如果有 lazy load, 记得使用 @Transactional
     */
    override fun full(): E =
            run {
                toMemberPropValues().forEach {
                    if (it is PersistentBag)
                        it.size
                }
                asChild()
            }
}