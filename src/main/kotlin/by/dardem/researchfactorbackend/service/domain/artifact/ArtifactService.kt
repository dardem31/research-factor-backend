package by.dardem.researchfactorbackend.service.domain.artifact

import by.dardem.researchfactorbackend.domain.entity.Artifact
import by.dardem.researchfactorbackend.repository.ArtifactRepository
import by.dardem.researchfactorbackend.service.base.BaseService
import org.springframework.stereotype.Service

@Service
class ArtifactService(
    private val artifactRepository: ArtifactRepository
) : BaseService<Artifact, Long>(artifactRepository) {
    
    suspend fun findAllById(ids: List<Long>): List<Artifact> =
        artifactRepository.findAllById(ids)
}
