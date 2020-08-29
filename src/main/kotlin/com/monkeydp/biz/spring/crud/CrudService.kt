package com.monkeydp.biz.spring.crud

import com.monkeydp.biz.spring.ex.BizEx
import com.monkeydp.biz.spring.result.ResultInfo
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

interface CrudService<E, ID> {

    fun save(entity: E): E

    fun saveAll(entities: Iterable<E>): List<E>

    fun find(spec: Specification<E>): E

    fun findOrNull(spec: Specification<E>): E?

    fun findById(id: ID): E

    fun findByIdOrNull(id: ID): E?

    fun findAll(query: AllQuery = AllQuery()): List<E>

    fun findAll(spec: Specification<E>, query: AllQuery = AllQuery()): List<E>

    fun findAll(query: PagingQuery): Paging<E>

    fun findAll(spec: Specification<E>, query: PagingQuery): Paging<E>

    fun first(query: FirstQuery = FirstQuery()): E

    fun first(spec: Specification<E>, query: FirstQuery = FirstQuery()): E

    fun delete(entity: E)

    fun deleteById(id: ID)

    fun delete(spec: Specification<E>)

    fun deleteAll(spec: Specification<E>)

    fun count(): Long

    fun count(spec: Specification<E>): Long

    fun has(spec: Specification<E>): Boolean

    fun has(spec: () -> Specification<E>): Boolean
}

abstract class AbstractCrudService<E : Any, ID, R : CrudRepo<E, ID>> : CrudService<E, ID> {

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

    override fun findAll(query: AllQuery): List<E> =
            repo.findAll(query.sort)

    override fun findAll(spec: Specification<E>, query: AllQuery): List<E> =
            repo.findAll(spec, query.sort)

    override fun findAll(query: PagingQuery): Paging<E> =
            repo.findAll(query.pageable)
                    .run(::Paging)

    override fun findAll(spec: Specification<E>, query: PagingQuery): Paging<E> =
            repo.findAll(spec, query.pageable)
                    .run(::Paging)

    override fun first(query: FirstQuery): E =
            findAll(query).first()

    override fun first(spec: Specification<E>, query: FirstQuery): E =
            findAll(spec, query).first()

    override fun delete(entity: E) = repo.delete(entity)

    override fun deleteById(id: ID) {
        try {
            delete(findById(id))
        } catch (e: EmptyResultDataAccessException) {
            throw buildDataNotFoundEx(entityClass)
        }
    }

    override fun delete(spec: Specification<E>) {
        val entity = findOrNull(spec)
        entity ?: throw buildDataNotFoundEx(entityClass)
        delete(entity)
    }

    override fun deleteAll(spec: Specification<E>) =
            repo.deleteAll(findAll(spec))

    override fun count(): Long =
            repo.count()

    override fun count(spec: Specification<E>) =
            repo.count(spec)

    override fun has(spec: Specification<E>) =
            findAll(spec).isNotEmpty()

    override fun has(spec: () -> Specification<E>): Boolean =
            has(spec())

    abstract fun buildDataNotFoundEx(notFoundKClass: KClass<*>): DataNotFoundEx
}

abstract class DataNotFoundEx(info: ResultInfo, cause: Throwable? = null) : BizEx(info, cause)

@NoRepositoryBean
interface CrudRepo<E, ID> : JpaRepository<E, ID>, JpaSpecificationExecutor<E>
