package by.dardem.researchfactorbackend.repository.base

import io.smallrye.mutiny.coroutines.awaitSuspending
import jakarta.persistence.NoResultException
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.hibernate.reactive.mutiny.Mutiny
import org.hibernate.reactive.mutiny.Mutiny.Session

abstract class CriteriaBuilderResolverImpl<T>(
    private val sessionFactory: Mutiny.SessionFactory,
    private val clazz: Class<T>
) : CriteriaBuilderResolver<T> {
    override suspend fun findList(
        criteriaFunction: CriteriaFunction<T>
    ): List<T> =
        sessionFactory.withSession { session ->
            val context = prepareQueryContext(clazz, session)
            val predicates = criteriaFunction.apply(context.root, context.builder)

            session.createQuery(
                context.query.select(context.root).where(*predicates.toTypedArray<Predicate?>())
            ).resultList
        }.awaitSuspending()

    override suspend fun delete(
        criteriaFunction: CriteriaFunction<T>
    ) {
        sessionFactory.withSession { session ->
            val builder = session.criteriaBuilder
            val query = builder.createCriteriaDelete(clazz)
            val root = query.from(clazz)
            val predicates = criteriaFunction.apply(root, builder)

            session.createQuery(
                query.where(*predicates.toTypedArray<Predicate?>())
            ).executeUpdate()
        }.awaitSuspending()
    }

    override suspend fun find(criteriaFunction: CriteriaFunction<T>): T? =
        sessionFactory.withSession { session ->
            val context = prepareQueryContext(clazz, session)
            val predicates = criteriaFunction.apply(context.root, context.builder)

            session.createQuery(
                context.query.select(context.root)
                    .where(*predicates.toTypedArray())
            ).singleResultOrNull
        }.awaitSuspending()

    override suspend fun exists(criteriaFunction: CriteriaFunction<T>): Boolean =
        sessionFactory.withSession { session ->

            val builder = session.criteriaBuilder
            val query = builder.createQuery(Int::class.java)
            val root = query.from(clazz)

            val predicates = criteriaFunction.apply(root, builder)

            query.select(builder.literal(1))
                .where(*predicates.toTypedArray())

            session.createQuery(query)
                .setMaxResults(1)
                .resultList
        }.awaitSuspending().isNotEmpty()

    private fun <T> prepareQueryContext(clazz: Class<T>, session: Session): QueryContext<T> {
        val builder = session.criteriaBuilder
        val query = builder.createQuery(clazz)
        val root = query.from(clazz)
        return QueryContext(builder, query, root)
    }

    @JvmRecord
    private data class QueryContext<T>(val builder: CriteriaBuilder, val query: CriteriaQuery<T>, val root: Root<T>)

}