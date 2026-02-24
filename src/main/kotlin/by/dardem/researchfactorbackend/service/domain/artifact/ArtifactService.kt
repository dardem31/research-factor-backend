package by.dardem.researchfactorbackend.service.domain.artifact

import by.dardem.researchfactorbackend.domain.entity.research_line.Artifact
import by.dardem.researchfactorbackend.repository.entity.ArtifactRepository
import by.dardem.researchfactorbackend.service.domain.base.BaseService
import org.springframework.stereotype.Service

@Service
class ArtifactService(
    private val artifactRepository: ArtifactRepository
) : BaseService<Artifact, Long>(artifactRepository) {
    suspend fun existsByIdAndUserId(id: Long, userId: Long): Boolean =
        artifactRepository.existsByIdAndUserId(id, userId)

}
