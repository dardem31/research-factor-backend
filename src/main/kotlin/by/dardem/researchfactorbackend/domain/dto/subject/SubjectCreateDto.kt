package by.dardem.researchfactorbackend.domain.dto.subject

import java.math.BigDecimal

data class SubjectCreateDto(
    val groupId: Long,
    val code: String,
    val parameterFields: List<ParameterFieldCreateDto> = emptyList()
)

data class ParameterFieldCreateDto(
    val parameterId: Long,
    val currentValue: BigDecimal
)
