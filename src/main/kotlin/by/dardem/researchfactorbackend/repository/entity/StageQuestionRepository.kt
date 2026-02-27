package by.dardem.researchfactorbackend.repository.entity

import by.dardem.researchfactorbackend.domain.entity.research_line.StageQuestion
import by.dardem.researchfactorbackend.repository.base.ReactiveCrudDao

interface StageQuestionRepository : ReactiveCrudDao<StageQuestion, Long> {
    suspend fun findAllByResearchLineId(researchLineId: Long): List<StageQuestion>
    suspend fun deleteById(id: Long)
}
