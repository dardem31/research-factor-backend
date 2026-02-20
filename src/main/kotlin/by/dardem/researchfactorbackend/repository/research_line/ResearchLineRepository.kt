package by.dardem.researchfactorbackend.repository.research_line

import by.dardem.researchfactorbackend.domain.entity.research_line.ResearchLine
import by.dardem.researchfactorbackend.repository.base.ReactiveCrudDao

interface ResearchLineRepository : ReactiveCrudDao<ResearchLine, Long> {
    suspend fun existsByIdAndUserId(id: Long, userId: Long): Boolean
}
