package by.dardem.researchfactorbackend.repository.entity

import by.dardem.researchfactorbackend.domain.entity.research_line.ResearchTask
import by.dardem.researchfactorbackend.repository.base.ReactiveCrudDao

interface ResearchTaskRepository : ReactiveCrudDao<ResearchTask, Long> {
    suspend fun existsByIdAndUserId(id: Long, userId: Long): Boolean
    suspend fun findByIdAndUserId(id: Long, userId: Long): ResearchTask?
    suspend fun findAllByResearchLineIdAndUserId(researchLineId: Long, userId: Long): List<ResearchTask>
    suspend fun deleteByIdAndUserId(id: Long, userId: Long)
}
