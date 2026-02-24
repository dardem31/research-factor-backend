package by.dardem.researchfactorbackend.repository.base.view

import by.dardem.researchfactorbackend.domain.dto.filter.ListFilter
import by.dardem.researchfactorbackend.domain.util.SearchResultDto
import by.dardem.researchfactorbackend.repository.base.CriteriaFunction

interface SearchCriteriaResolver<T> {
    suspend fun resolvePredicates(
        searchCriteria: ListFilter,
        criteriaFunction: CriteriaFunction<T>
    ): SearchResultDto<T>

    suspend fun countPredicates(criteriaFunction: CriteriaFunction<T>): Long
    suspend fun find(criteriaFunction: CriteriaFunction<T>): T?
    suspend fun findList(criteriaFunction: CriteriaFunction<T>): List<T>
}