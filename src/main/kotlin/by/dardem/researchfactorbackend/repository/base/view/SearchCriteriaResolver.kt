package by.dardem.researchfactorbackend.repository.base.view

import by.dardem.researchfactorbackend.domain.filter.ListFilter
import by.dardem.researchfactorbackend.domain.util.SearchResultDto
import by.dardem.researchfactorbackend.repository.base.CriteriaFunction

interface SearchCriteriaResolver<T> {
    suspend fun <R> resolvePredicates(
        searchCriteria: ListFilter,
        criteriaFunction: CriteriaFunction<T>,
        convertToDto: (T) -> R
    ): SearchResultDto<R>

    suspend fun resolvePredicates(
        searchCriteria: ListFilter,
        criteriaFunction: CriteriaFunction<T>
    ): SearchResultDto<T>

    suspend fun countPredicates(criteriaFunction: CriteriaFunction<T>): Long
}