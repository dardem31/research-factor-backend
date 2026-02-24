package by.dardem.researchfactorbackend.domain.dto.filter.searchCriteria

import by.dardem.researchfactorbackend.domain.enums.ResearchStatus

data class ResearchCriteriaDto(
    val title: String? = null,
    val status: ResearchStatus? = null,
)