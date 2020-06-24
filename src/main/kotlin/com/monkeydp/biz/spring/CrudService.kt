package com.monkeydp.biz.spring

import com.monkeydp.tools.ext.kotlin.singleton
import com.monkeydp.tools.util.TypeUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean
import kotlin.properties.Delegates
import kotlin.reflect.KClass

interface CrudService<E : Any, ID : Any> {

    fun save(entity: E): E

    fun saveAll(entities: Iterable<E>): List<E>

    fun find(spec: Specification<E>): E

    fun findOrNull(spec: Specification<E>): E?

    fun findById(id: ID): E

    fun findByIdOrNull(id: ID): E?

    fun findAll(): List<E>

    fun findAll(spec: Specification<E>): List<E>

    fun findAll(pagingQuery: PagingQuery): Paging<E>

    fun findAll(spec: Specification<E>, pagingQuery: PagingQuery): Paging<E>

    fun delete(entity: E)

    fun deleteById(id: ID)

    fun delete(spec: Specification<E>)

    fun count(): Long

    fun has(spec: Specification<E>): Boolean

    fun has(spec: () -> Specification<E>): Boolean
}

abstract class AbstractCrudService<E : Any, ID : Any, R : CrudRepo<E, ID>> : CrudService<E, ID> {

    @set:Autowired
    private var repo: R by Delegates.singleton()

    private val entityClass: KClass<E> = TypeUtil.getGenericType<Class<E>>(this).kotlin

    override fun save(entity: E): E = repo.save(entity)

    override fun saveAll(entities: Iterable<E>) = repo.saveAll(entities)

    override fun find(spec: Specification<E>): E =
        repo.findOne(spec)
            .orElseThrow { throw buildDataNotFoundEx(entityClass) }

    override fun findOrNull(spec: Specification<E>): E? =
        repo.findOne(spec)
            .orElse(null)

    override fun findById(id: ID): E =
        repo.findById(id)
            .orElseThrow { throw buildDataNotFoundEx(entityClass) }

    override fun findByIdOrNull(id: ID): E? =
        repo.findById(id)
            .orElse(null)

    override fun findAll(): List<E> = repo.findAll()

    override fun findAll(spec: Specification<E>): List<E> =
        repo.findAll(spec)

    override fun findAll(pagingQuery: PagingQuery): Paging<E> =
        repo.findAll(pagingQuery.pageable)
            .run(::Paging)

    override fun findAll(spec: Specification<E>, pagingQuery: PagingQuery): Paging<E> =
        repo.findAll(spec, pagingQuery.pageable)
            .run(::Paging)

    override fun delete(entity: E) = repo.delete(entity)

    override fun deleteById(id: ID) {
        try {
            repo.deleteById(id)
        } catch (e: EmptyResultDataAccessException) {
            throw buildDataNotFoundEx(entityClass)
        }
    }

    override fun delete(spec: Specification<E>) {
        val entity = findOrNull(spec)
        entity ?: throw buildDataNotFoundEx(entityClass)
        delete(entity)
    }

    override fun count(): Long =
        repo.count()

    override fun has(spec: Specification<E>) =
        findOrNull(spec) != null

    override fun has(spec: () -> Specification<E>): Boolean =
        has(spec())

    abstract class DataNotFoundEx : Throwable()

    abstract fun buildDataNotFoundEx(notFoundKClass: KClass<*>): DataNotFoundEx
}

@NoRepositoryBean
interface CrudRepo<E : Any, ID : Any> : JpaRepository<E, ID>, JpaSpecificationExecutor<E>