package by.dardem.researchfactorbackend.domain.dto.execution

import java.math.BigDecimal

data class LogEntryCreateDto(
    val taskId: Long,
    val text: String,
    val artifactIds: List<Long> = emptyList(),
    val subjectUpdates: List<SubjectUpdateDto> = emptyList()
)

data class SubjectUpdateDto(
    val subjectId: Long,
    val parameterChanges: List<ParameterChangeDto>
)

data class ParameterChangeDto(
    val parameterId: Long,
    val previousValue: BigDecimal? = null,
    val newValue: BigDecimal
)
