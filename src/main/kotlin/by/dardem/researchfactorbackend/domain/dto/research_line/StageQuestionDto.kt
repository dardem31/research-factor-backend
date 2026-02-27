package by.dardem.researchfactorbackend.domain.dto.research_line

data class StageQuestionDto(
    var id: Long? = 0,
    val researchLineId: Long,
    val text: String,
    val status: String = "DRAFT"
)
