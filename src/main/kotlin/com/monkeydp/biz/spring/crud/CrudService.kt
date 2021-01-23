package com.monkeydp.biz.spring.crud

import au.com.console.jpaspecificationdsl.where
import com.monkeydp.biz.spring.ex.BizEx
import com.monkeydp.biz.spring.result.ResultInfo
import com.monkeydp.tools.ext.kotlin.getFieldValue
import com.monkeydp.tools.ext.kotlin.singleton
import com.monkeydp.tools.util.TypeUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.transaction.annotation.Transactional
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root
import kotlin.properties.Delegates
import kotlin.reflect.KClass

interface CrudService<E, ID> {

    fun E.getId(): ID =
            getFieldValue("id") { forceAccess = true }

    fun create(
            entity: E,
            config: (CreateConfig<E>.() -> Unit)? = null,
    ): E

    fun createAll(
            entities: Iterable<E>,
            config: (CreateAllConfig<E>.() -> Unit)? = null,
    ): List<E>


    fun update(
            entity: E,
            config: (UpdateConfig<E>.() -> Unit)? = null,
    ): E


    fun updateAll(
            entities: Iterable<E>,
            config: (UpdateAllConfig<E>.() -> Unit)? = null,
    ): List<E>

    fun save(
            entity: E,
            config: (SaveConfig<E>.() -> Unit)? = null,
    ): E

    fun saveAll(
            entities: Iterable<E>,
            config: (SaveAllConfig<E>.() -> Unit)? = null,
    ): List<E>

    fun find(spec: Specification<E>): E

    fun find(makePredicate: CriteriaBuilder.(Root<E>) -> Predicate): E

    fun findOrNull(spec: Specification<E>): E?

    fun findOrNull(makePredicate: CriteriaBuilder.(Root<E>) -> Predicate): E?

    fun findById(id: ID): E

    fun findByIdOrNull(id: ID?): E?

    fun findAll(query: AllQuery = AllQuery()): List<E>

    fun findAll(spec: Specification<E>, query: AllQuery = AllQuery()): List<E>

    fun findAll(query: PagingQuery): Paging<E>

    fun findAll(spec: Specification<E>, query: PagingQuery): Paging<E>

    fun findAllById(ids: Iterable<ID>): List<E>

    fun first(query: FirstQuery = FirstQuery()): E

    fun first(spec: Specification<E>, query: FirstQuery = FirstQuery()): E

    fun firstOrNull(query: FirstQuery = FirstQuery()): E?

    fun firstOrNull(spec: Specification<E>, query: FirstQuery = FirstQuery()): E?

    fun delete(entity: E)

    fun deleteById(id: ID)

    fun delete(spec: Specification<E>)

    fun deleteMaybeNull(spec: Specification<E>): E?

    fun deleteByIdMaybeNull(id: ID): E?

    /**
     * 不是批量操作
     */
    fun deleteAll(entities: Iterable<E>)

    fun deleteAll(spec: Specification<E>)

    fun deleteInBatch(entities: Iterable<E>)

    fun deleteInBatch(spec: Specification<E>)

    fun count(): Long

    fun count(spec: Specification<E>): Long

    fun exist(spec: Specification<E>): Boolean

    fun exist(spec: () -> Specification<E>): Boolean

    fun existById(id: ID): Boolean

    fun existById(ids: Iterable<ID>): Boolean

    fun checkExist(spec: Specification<E>)

    fun checkExist(spec: () -> Specification<E>)

    fun checkExistById(id: ID)

    fun checkExistById(ids: Iterable<ID>)

    fun existNo(spec: Specification<E>): Boolean

    fun existNo(spec: () -> Specification<E>): Boolean

    fun existNoById(id: ID): Boolean

    fun existNoById(ids: Iterable<ID>): Boolean

    fun checkExistNo(spec: Specification<E>)

    fun checkExistNo(spec: () -> Specification<E>)

    fun checkExistNoById(id: ID)

    fun checkExistNoById(ids: Iterable<ID>)
}

abstract class AbstractCrudService<E : Any, ID : Any, R : CrudRepo<E, ID>> : CrudService<E, ID> {

    @set:Autowired
    protected var repo: R by Delegates.singleton()

    private val entityClass: KClass<E> = TypeUtil.getGenericType<Class<E>>(this).kotlin

    @Transactional
    override fun create(entity: E, config: ((CreateConfig<E>) -> Unit)?) =
            CreateConfig(config).run {
                if (enableCheckExistNo)
                    checkExistNoById(entity.getId())
                entity.checkDuplicate()
                repo.save(entity)
            }

    @Transactional
    override fun createAll(entities: Iterable<E>, config: (CreateAllConfig<E>.() -> Unit)?) =
            CreateAllConfig(config).run {
                if (enableCheckExistNo)
                    checkExistNoById(entities.map { it.getId() })
                entities.checkDuplicate()
                repo.saveAll(entities)
            }

    @Transactional
    override fun update(entity: E, config: (UpdateConfig<E>.() -> Unit)?) =
            UpdateConfig(config).run {
                if (enableCheckExist)
                    checkExistById(entity.getId())
                entity.checkDuplicate()
                repo.save(entity)
            }

    @Transactional
    override fun updateAll(entities: Iterable<E>, config: (UpdateAllConfig<E>.() -> Unit)?): List<E> =
            UpdateAllConfig(config).run {
                if (enableCheckExist)
                    checkExistById(entities.map { it.getId() })
                entities.checkDuplicate()
                repo.saveAll(entities)
            }

    @Transactional
    override fun save(entity: E, config: (SaveConfig<E>.() -> Unit)?): E =
            SaveConfig(config).run {
                checkDuplicate(entity)
                repo.save(entity)
            }

    @Transactional
    override fun saveAll(entities: Iterable<E>, config: (SaveAllConfig<E>.() -> Unit)?) =
            SaveAllConfig(config).run {
                checkDuplicate(entities)
                repo.saveAll(entities)
            }

    override fun find(spec: Specification<E>): E =
            repo.findOne(spec)
                    .orElseThrow { throwDataNotExistEx() }

    override fun find(makePredicate: CriteriaBuilder.(Root<E>) -> Predicate) =
            find(where(makePredicate))

    override fun findOrNull(spec: Specification<E>): E? =
            repo.findOne(spec)
                    .orElse(null)

    override fun findOrNull(makePredicate: CriteriaBuilder.(Root<E>) -> Predicate) =
            findOrNull(where(makePredicate))

    override fun findById(id: ID): E =
            repo.findById(id)
                    .orElseThrow { throwDataNotExistEx() }

    override fun findByIdOrNull(id: ID?): E? =
            if (id == null) null
            else repo.findById(id)
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

    override fun findAllById(ids: Iterable<ID>) =
            repo.findAllById(ids)

    override fun first(query: FirstQuery): E {
        val entity = firstOrNull(query)
        if (entity == null) throwDataNotExistEx()
        return entity
    }

    override fun first(spec: Specification<E>, query: FirstQuery): E =
            findAll(spec, query).first()

    override fun firstOrNull(query: FirstQuery): E? =
            findAll(query).firstOrNull()

    override fun firstOrNull(spec: Specification<E>, query: FirstQuery): E? =
            findAll(spec, query).firstOrNull()

    @Transactional
    override fun delete(entity: E) =
            repo.delete(entity)

    @Transactional
    override fun deleteById(id: ID) {
        try {
            delete(findById(id))
        } catch (e: EmptyResultDataAccessException) {
            throw buildDataNotExistFoundEx(entityClass)
        }
    }

    @Transactional
    override fun delete(spec: Specification<E>) {
        val entity = findOrNull(spec)
        entity ?: throwDataNotExistEx()
        delete(entity)
    }

    @Transactional
    override fun deleteMaybeNull(spec: Specification<E>) =
            findOrNull(spec)?.apply {
                run(::delete)
            }

    override fun deleteByIdMaybeNull(id: ID): E? =
            findByIdOrNull(id)?.apply {
                run(::delete)
            }
    
    @Transactional
    override fun deleteAll(entities: Iterable<E>) {
        repo.deleteAll(entities)
    }

    @Transactional
    override fun deleteAll(spec: Specification<E>) =
            deleteAll(findAll(spec))

    @Transactional
    override fun deleteInBatch(entities: Iterable<E>) {
        repo.deleteInBatch(entities)
    }

    @Transactional
    override fun deleteInBatch(spec: Specification<E>) {
        deleteInBatch(findAll(spec))
    }

    override fun count(): Long =
            repo.count()

    override fun count(spec: Specification<E>) =
            repo.count(spec)

    override fun exist(spec: Specification<E>) =
            firstOrNull(spec) != null

    override fun exist(spec: () -> Specification<E>) =
            exist(spec())

    override fun existById(id: ID) =
            repo.existsById(id)

    override fun existById(ids: Iterable<ID>) =
            findAllById(ids).size == ids.toCollection(mutableListOf()).size

    override fun checkExist(spec: Specification<E>) {
        if (existNo(spec))
            throwDataNotExistEx()
    }

    override fun checkExist(spec: () -> Specification<E>) {
        checkExist(spec())
    }

    override fun checkExistById(id: ID) {
        if (existNoById(id))
            throwDataNotExistEx()
    }

    override fun checkExistById(ids: Iterable<ID>) {
        if (existNoById(ids))
            throwDataNotExistEx()
    }

    override fun existNo(spec: Specification<E>) =
            !exist(spec)

    override fun existNo(spec: () -> Specification<E>) =
            !exist(spec)

    override fun existNoById(id: ID) =
            !existById(id)

    override fun existNoById(ids: Iterable<ID>) =
            findAllById(ids).isEmpty()

    override fun checkExistNo(spec: Specification<E>) {
        if (exist(spec))
            throwDataAlreadyExistEx()
    }

    override fun checkExistNo(spec: () -> Specification<E>) {
        checkExistNo(spec())
    }

    override fun checkExistNoById(id: ID) {
        if (existById(id))
            throwDataAlreadyExistEx()
    }

    override fun checkExistNoById(ids: Iterable<ID>) {
        if (existById(ids))
            throwDataAlreadyExistEx()
    }

    private fun throwDataAlreadyExistEx(): Nothing {
        throw buildDataAlreadyExistEx()
    }

    abstract fun buildDataAlreadyExistEx(kClass: KClass<*> = entityClass): DataAlreadyExistEx

    private fun throwDataNotExistEx(): Nothing {
        throw buildDataNotExistFoundEx()
    }

    abstract fun buildDataNotExistFoundEx(kClass: KClass<*> = entityClass): DataNotExistEx
}

abstract class DataAlreadyExistEx(info: ResultInfo, cause: Throwable? = null) : BizEx(info, cause)

abstract class DataNotExistEx(info: ResultInfo, cause: Throwable? = null) : BizEx(info, cause)

@NoRepositoryBean
interface CrudRepo<E, ID> : JpaRepository<E, ID>, JpaSpecificationExecutor<E>
