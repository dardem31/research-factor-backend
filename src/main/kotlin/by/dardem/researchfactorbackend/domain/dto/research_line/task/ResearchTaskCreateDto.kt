package by.dardem.researchfactorbackend.domain.dto.research_line.task

import by.dardem.researchfactorbackend.domain.enums.ResearchTaskStatus

data class ResearchTaskCreateDto(
    val researchLineId: Long,
    val title: String,
    val description: String,
    val status: ResearchTaskStatus = ResearchTaskStatus.OPEN
)
