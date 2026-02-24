package by.dardem.researchfactorbackend.repository.entity

import by.dardem.researchfactorbackend.domain.entity.research_line.Artifact
import by.dardem.researchfactorbackend.repository.base.ReactiveCrudDao

interface ArtifactRepository : ReactiveCrudDao<Artifact, Long> {
    suspend fun existsByIdAndUserId(id: Long, userId: Long): Boolean
}