package by.dardem.researchfactorbackend.domain.dto.subject

data class SubjectGroupCreateDto(
    val researchId: Long,
    val label: String,
    val description: String? = null
)
