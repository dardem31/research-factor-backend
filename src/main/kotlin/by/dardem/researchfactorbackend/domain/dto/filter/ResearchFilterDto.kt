package by.dardem.researchfactorbackend.domain.dto.filter

import by.dardem.researchfactorbackend.domain.dto.filter.searchCriteria.ResearchCriteriaDto

data class ResearchFilterDto (
    val filter: ResearchCriteriaDto? = ResearchCriteriaDto()
) : ListFilter()