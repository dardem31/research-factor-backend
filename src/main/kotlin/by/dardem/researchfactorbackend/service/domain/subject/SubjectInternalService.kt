package by.dardem.researchfactorbackend.service.domain.subject

import by.dardem.researchfactorbackend.domain.entity.subject.Subject
import by.dardem.researchfactorbackend.repository.subject.SubjectRepository
import by.dardem.researchfactorbackend.service.domain.base.BaseService
import org.springframework.stereotype.Service

@Service
class SubjectInternalService(
    private val subjectRepository: SubjectRepository
) : BaseService<Subject, Long>(subjectRepository) {
    
    suspend fun getUserIdById(id: Long): Long? = 
        findById(id)?.userId
}
