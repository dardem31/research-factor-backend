package by.dardem.researchfactorbackend.domain.dto.research_line

import by.dardem.researchfactorbackend.domain.enums.ResearchLineStatus
import java.time.Instant

data class ResearchLineDto(
    var id: Long? = 0,
    val researchId: Long,
    val sequenceOrder: Int,
    val title: String,
    val duration: String? = null,
    val status: ResearchLineStatus = ResearchLineStatus.LOCKED,
    val plannedStartDate: Instant? = null,
    val plannedEndDate: Instant? = null
)
