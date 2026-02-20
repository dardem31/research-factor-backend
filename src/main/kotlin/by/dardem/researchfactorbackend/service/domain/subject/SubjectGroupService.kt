package by.dardem.researchfactorbackend.service.domain.subject

import by.dardem.researchfactorbackend.domain.dto.subject.SubjectGroupCreateDto
import by.dardem.researchfactorbackend.domain.entity.subject.SubjectGroup
import by.dardem.researchfactorbackend.repository.subject.SubjectGroupRepository
import by.dardem.researchfactorbackend.service.domain.base.BaseService
import by.dardem.researchfactorbackend.service.domain.research.ResearchService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class SubjectGroupService(
    private val subjectGroupRepository: SubjectGroupRepository,
    private val researchService: ResearchService
) : BaseService<SubjectGroup, Long>(subjectGroupRepository) {

    suspend fun existsByIdAndUserId(id: Long, userId: Long): Boolean =
        subjectGroupRepository.existsByIdAndUserId(id, userId)

    suspend fun createSubjectGroup(dto: SubjectGroupCreateDto, userId: Long): Long {
        // Проверяем владение исследованием через ResearchService
        val isOwner = researchService.existsByIdAndUserId(dto.researchId, userId)
        if (!isOwner) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Research not found or you don't have permission")
        }

        val group = SubjectGroup(
            researchId = dto.researchId,
            userId = userId,
            label = dto.label,
            description = dto.description
        )

        val savedGroup = save(group)
        return savedGroup.id!!
    }
}
