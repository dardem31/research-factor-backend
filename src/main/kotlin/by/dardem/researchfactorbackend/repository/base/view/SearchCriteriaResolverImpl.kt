package by.dardem.researchfactorbackend.repository.base.view

import by.dardem.researchfactorbackend.domain.dto.filter.ListFilter
import by.dardem.researchfactorbackend.domain.dto.filter.PageableDto
import by.dardem.researchfactorbackend.domain.enums.PaginationOrderEnum
import by.dardem.researchfactorbackend.domain.util.SearchResultDto
import by.dardem.researchfactorbackend.repository.base.CriteriaFunction
import io.smallrye.mutiny.coroutines.awaitSuspending
import jakarta.persistence.criteria.*
import org.hibernate.reactive.mutiny.Mutiny
import org.hibernate.reactive.mutiny.Mutiny.Session

open class SearchCriteriaResolverImpl<T>(
    private val clazz: Class<T>,
    private val sessionFactory: Mutiny.SessionFactory
) : SearchCriteriaResolver<T> {

    override suspend fun resolvePredicates(
        searchCriteria: ListFilter,
        criteriaFunction: CriteriaFunction<T>
    ): SearchResultDto<T> {
        val pagination: PageableDto = searchCriteria.pagination

        pagination.limit += 1
        var searchResult = resolvePredicates(criteriaFunction, pagination)
        val hasMore = searchResult.size == pagination.limit
        if (hasMore) {
            searchResult = if (PaginationOrderEnum.prev == searchCriteria.pagination.order) {
                searchResult.drop(1)
            } else {
                searchResult.dropLast(1)
            }
        }

        return SearchResultDto(searchResult, hasMore)
    }

    override suspend fun countPredicates(
        criteriaFunction: CriteriaFunction<T>
    ): Long = sessionFactory.withSession { session ->
        val cb = session.criteriaBuilder
        val countQuery = cb.createQuery(Long::class.java)

        val root = countQuery.from(clazz)
        val predicates = criteriaFunction.apply(root, cb)

        countQuery.select(cb.count(root)).where(*predicates.toTypedArray())

        session.createQuery(countQuery).singleResult
    }.awaitSuspending()

    private suspend fun resolvePredicates(
        criteriaFunction: CriteriaFunction<T>,
        pagination: PageableDto
    ): List<T> {
        var order: Order? = null
        return sessionFactory.withSession { session ->
            val context = prepareQueryContext(clazz, session)
            val predicates = criteriaFunction.apply(context.root, context.builder)
            val sortableField = getSortableField(clazz)
            order = pagination.apply(
                predicates,
                context.root.get(sortableField.fieldName),
                context.builder,
                sortableField.type
            )
            val finalOrder = order ?: context.builder.desc(context.root.get<Comparable<Any>>(sortableField.fieldName))
            session.createQuery(
                context.query.select(context.root).where(
                    *predicates.toTypedArray()
                ).orderBy(finalOrder)
            ).setMaxResults(pagination.limit)
                .resultList
        }.awaitSuspending()
            .let { list ->
                if (order?.isAscending == true) list.reversed() else list
            }
    }
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

    override suspend fun find(criteriaFunction: CriteriaFunction<T>): T? =
        sessionFactory.withSession { session ->
            val context = prepareQueryContext(clazz, session)
            val predicates = criteriaFunction.apply(context.root, context.builder)

            session.createQuery(
                context.query.select(context.root)
                    .where(*predicates.toTypedArray())
            ).singleResultOrNull
        }.awaitSuspending()

    companion object {

        fun <T> getSortableField(clazz: Class<T>): SortableField {
            return SortableField(fieldName = "id", type = Long::class.java)
//            return when (clazz) {
//                TemplateOverviewItem::class.java -> SortableField(fieldName = "id", type = Long::class.java)
//                TemplateLink::class.java -> SortableField(fieldName = "id", type = Long::class.java)
//                MessageOverviewItem::class.java -> SortableField(fieldName = "id", type = Long::class.java)
//                else -> throw IllegalArgumentException("Unsupported type: $clazz")
//            }
        }
    }

    private fun <T> prepareQueryContext(clazz: Class<T>, session: Session): QueryContext<T> {
        val builder = session.criteriaBuilder
        val query = builder.createQuery(clazz)
        val root = query.from(clazz)
        return QueryContext(builder, query, root)
    }
    @JvmRecord
    private data class QueryContext<T>(val builder: CriteriaBuilder, val query: CriteriaQuery<T>, val root: Root<T>)

    data class SortableField(
        var value: Comparable<Any>? = null,
        val fieldName: String,
        val type: Class<*>
    )
}