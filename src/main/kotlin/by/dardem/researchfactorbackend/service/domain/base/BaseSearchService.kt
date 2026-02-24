package by.dardem.researchfactorbackend.service.domain.base

import by.dardem.researchfactorbackend.domain.dto.filter.ListFilter
import by.dardem.researchfactorbackend.domain.util.SearchResultDto
import by.dardem.researchfactorbackend.repository.base.CriteriaFunction
import by.dardem.researchfactorbackend.repository.base.view.SearchCriteriaResolver

abstract class BaseSearchService<T>(private val dao: SearchCriteriaResolver<T>) {

    suspend fun list(
        messageListFilterDto: ListFilter,
        criteriaFunction: CriteriaFunction<T>
    ): SearchResultDto<T> {
        return dao.resolvePredicates(messageListFilterDto, criteriaFunction)
    }

    suspend fun count(criteriaFunction: CriteriaFunction<T>): Long {
        return dao.countPredicates(criteriaFunction)
    }
}