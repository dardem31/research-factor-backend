package by.dardem.researchfactorbackend.domain.dto.research

data class ResearchCreateDto(
    val title: String,
    val hypothesis: String,
    val description: String,
    val protocol: ProtocolCreateDto,
    val primaryOutcomes: List<PrimaryOutcomeCreateDto>,
    val trackedParameters: List<TrackedParameterCreateDto>
)

data class ProtocolCreateDto(
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

data class PrimaryOutcomeCreateDto(
    val text: String
)

data class TrackedParameterCreateDto(
    val name: String,
    val unit: String
)
