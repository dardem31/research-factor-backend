package by.dardem.researchfactorbackend.repository.entity

import by.dardem.researchfactorbackend.domain.entity.subject.Subject
import by.dardem.researchfactorbackend.repository.base.ReactiveCrudDao

interface SubjectRepository : ReactiveCrudDao<Subject, Long> {
    suspend fun existsByIdAndUserId(id: Long, userId: Long): Boolean
}
