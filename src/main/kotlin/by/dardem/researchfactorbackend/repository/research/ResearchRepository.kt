package by.dardem.researchfactorbackend.repository.research

import by.dardem.researchfactorbackend.domain.entity.research.Research
import by.dardem.researchfactorbackend.repository.base.ReactiveCrudDao

interface ResearchRepository : ReactiveCrudDao<Research, Long> {
    suspend fun existsByIdAndUserId(id: Long, userId: Long): Boolean
}
