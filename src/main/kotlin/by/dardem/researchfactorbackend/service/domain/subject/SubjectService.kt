package by.dardem.researchfactorbackend.service.domain.subject

import by.dardem.researchfactorbackend.domain.dto.subject.SubjectCreateDto
import by.dardem.researchfactorbackend.domain.entity.subject.ParameterField
import by.dardem.researchfactorbackend.domain.entity.subject.Subject
import by.dardem.researchfactorbackend.repository.entity.SubjectRepository
import by.dardem.researchfactorbackend.service.domain.base.BaseService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class SubjectService(
    private val subjectRepository: SubjectRepository,
    private val subjectGroupService: SubjectGroupService
) : BaseService<Subject, Long>(subjectRepository) {

    suspend fun createSubject(dto: SubjectCreateDto, userId: Long): Long {
        // Проверяем владение группой через SubjectGroupService
        val isGroupOwner = subjectGroupService.existsByIdAndUserId(dto.groupId, userId)
        if (!isGroupOwner) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Subject group not found or you don't have permission")
        }

        val subject = Subject(
            groupId = dto.groupId,
            userId = userId,
            code = dto.code
        )

        // Маппим параметры, если они переданы
        subject.parameterFields = dto.parameterFields.map {
            ParameterField(
                subject = subject,
                parameterId = it.parameterId,
                currentValue = it.currentValue
            )
        }.toMutableList()

        val savedSubject = save(subject)
        return savedSubject.id!!
    }
    suspend fun existsByIdAndUserId(id: Long, userId: Long): Boolean =
        subjectRepository.existsByIdAndUserId(id, userId)
}
