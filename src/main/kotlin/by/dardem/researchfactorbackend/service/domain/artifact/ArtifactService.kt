package by.dardem.researchfactorbackend.service.domain.artifact

import by.dardem.researchfactorbackend.domain.dto.mappers.ArtifactMapper
import by.dardem.researchfactorbackend.domain.dto.research_line.artifact.ArtifactDto
import by.dardem.researchfactorbackend.domain.entity.research_line.Artifact
import by.dardem.researchfactorbackend.repository.entity.ArtifactRepository
import by.dardem.researchfactorbackend.service.domain.base.BaseService
import by.dardem.researchfactorbackend.service.domain.research_line.task.ResearchTaskService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ArtifactService(
    private val artifactRepository: ArtifactRepository,
    private val researchTaskService: ResearchTaskService,
    private val artifactMapper: ArtifactMapper
) : BaseService<Artifact, Long>(artifactRepository) {
    suspend fun existsByIdAndUserId(id: Long, userId: Long): Boolean =
        artifactRepository.existsByIdAndUserId(id, userId)

    suspend fun createArtifact(dto: ArtifactDto, userId: Long): ArtifactDto {
        researchTaskService.validateTaskOwnership(dto.taskId, userId)

        val artifact = artifactMapper.toEntity(dto).apply {
            this.userId = userId
            // Устанавливаем заглушки как просили
            this.storageUrl = ""
            this.sha256 = ""
        }

        val savedArtifact = save(artifact)
        return artifactMapper.toDto(savedArtifact)
    }

    suspend fun updateArtifact(dto: ArtifactDto, userId: Long): ArtifactDto {
        val id = dto.id ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Artifact id is required")

        val artifact = artifactRepository.findByIdAndUserId(id, userId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Artifact not found")

        artifactMapper.updateEntityFromDto(dto, artifact)
        val savedArtifact = saveOrUpdate(artifact)
        return artifactMapper.toDto(savedArtifact)
    }

    suspend fun deleteArtifact(id: Long, userId: Long) {
        val exists = artifactRepository.existsByIdAndUserId(id, userId)
        if (!exists) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Artifact not found")
        }
        artifactRepository.deleteByIdAndUserId(id, userId)
    }

    suspend fun getArtifactsByTaskId(taskId: Long, userId: Long): List<ArtifactDto> {
        return artifactRepository.findAllByTaskIdAndUserId(taskId, userId)
            .map(artifactMapper::toDto)
    }
}
