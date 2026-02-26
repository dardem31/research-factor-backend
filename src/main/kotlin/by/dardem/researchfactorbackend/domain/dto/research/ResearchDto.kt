package by.dardem.researchfactorbackend.domain.dto.research

data class ResearchDto(
    val id: Long,
    val title: String,
    val hypothesis: String,
    val description: String,
    val protocol: ProtocolDto,
    val primaryOutcomes: List<PrimaryOutcomeDto>,
    val trackedParameters: List<TrackedParameterDto>
)

data class ProtocolDto(
    val id: Long,
    val primaryOutcome: String,
    val sampleSizeJustification: String,
    val statisticalMethod: String,
    val randomizationMethod: String,
    val blindingDetails: String,
    val interventionDescription: String,
    val inclusionCriteria: String,
    val exclusionCriteria: String,
    val earlyStoppingCriteria: String
)

data class PrimaryOutcomeDto(
    val id: Long,
    val text: String
)

data class TrackedParameterDto(
    val id: Long,
    val name: String,
    val unit: String
)
