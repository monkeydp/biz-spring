package com.monkeydp.biz.spring.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.monkeydp.tools.ext.kotlin.toJson
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.MappedSuperclass

interface Entity {
    var createdAt: Date
    var updatedAt: Date

    /**
     * 是否和另一个实体相同
     */
    fun <E : Entity> isSameAs(another: E): Boolean
}

@MappedSuperclass
abstract class AbstractEntity : Entity, Serializable {

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

    override fun <E : Entity> isSameAs(another: E): Boolean =
        toJson() == another.toJson()
}