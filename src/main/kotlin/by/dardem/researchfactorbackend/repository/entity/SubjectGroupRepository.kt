package by.dardem.researchfactorbackend.repository.entity

import by.dardem.researchfactorbackend.domain.entity.subject.SubjectGroup
import by.dardem.researchfactorbackend.repository.base.ReactiveCrudDao

interface SubjectGroupRepository : ReactiveCrudDao<SubjectGroup, Long> {
    suspend fun existsByIdAndUserId(id: Long, userId: Long): Boolean
}
