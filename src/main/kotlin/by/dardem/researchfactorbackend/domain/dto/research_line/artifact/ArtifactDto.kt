package by.dardem.researchfactorbackend.domain.dto.research_line.artifact

import by.dardem.researchfactorbackend.domain.enums.ArtifactType
import java.time.Instant

data class ArtifactDto(
    var id: Long? = 0,
    val taskId: Long,
    val type: ArtifactType,
    val storageUrl: String? = null,
    val sha256: String? = null,
    val metadata: String? = null,
    val createdAt: Instant? = null
)
