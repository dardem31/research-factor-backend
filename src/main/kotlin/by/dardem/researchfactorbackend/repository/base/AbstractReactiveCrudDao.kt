package by.dardem.researchfactorbackend.repository.base

import io.smallrye.mutiny.coroutines.awaitSuspending
import org.hibernate.reactive.mutiny.Mutiny

abstract class AbstractReactiveCrudDao<T, ID>(
    private val entityClass: Class<T>,
    protected val sessionFactory: Mutiny.SessionFactory
) : ReactiveCrudDao<T, ID>, CriteriaBuilderResolverImpl<T>(sessionFactory, entityClass) {

    override suspend fun findById(id: ID): T? =
        sessionFactory.withSession { session ->
            session.find(entityClass, id)
        }.awaitSuspending()

    override suspend fun findAll(): List<T> =
        sessionFactory.withSession { session ->
            val cb = session.criteriaBuilder
            val query = cb.createQuery(entityClass)
            query.from(entityClass)
            session.createQuery(query).resultList
        }.awaitSuspending()

    override suspend fun save(entity: T): T =
        sessionFactory.withTransaction { tx ->
            tx.persist(entity).replaceWith(entity)
        }.awaitSuspending()

    override suspend fun saveOrUpdate(entity: T): T =
        sessionFactory.withTransaction { tx ->
            tx.merge(entity).replaceWith(entity)
        }.awaitSuspending()

    override suspend fun saveAll(entities: Iterable<T>) {
        sessionFactory.withTransaction { tx ->
            tx.persistAll(entities).replaceWith(entities.toList())
        }.awaitSuspending()
    }
}