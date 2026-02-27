package by.dardem.researchfactorbackend.repository.entity

import by.dardem.researchfactorbackend.domain.entity.research_line.ResearchLine
import by.dardem.researchfactorbackend.repository.base.ReactiveCrudDao

interface ResearchLineRepository : ReactiveCrudDao<ResearchLine, Long> {
    suspend fun existsByIdAndUserId(id: Long, userId: Long): Boolean
    suspend fun findByIdAndUserId(id: Long, userId: Long): ResearchLine?
    suspend fun findAllByResearchIdAndUserId(researchId: Long, userId: Long): List<ResearchLine>
    suspend fun deleteByIdAndUserId(id: Long, userId: Long)
}
