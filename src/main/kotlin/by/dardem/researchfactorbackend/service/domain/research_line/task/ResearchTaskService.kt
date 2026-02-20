package by.dardem.researchfactorbackend.service.domain.research_line.task

import by.dardem.researchfactorbackend.domain.dto.research_line.task.ResearchTaskCreateDto
import by.dardem.researchfactorbackend.domain.entity.research_line.ResearchTask
import by.dardem.researchfactorbackend.repository.research_line.task.ResearchTaskRepository
import by.dardem.researchfactorbackend.service.domain.base.BaseService
import by.dardem.researchfactorbackend.service.domain.research_line.ResearchLineService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ResearchTaskService(
    private val researchTaskRepository: ResearchTaskRepository,
    private val researchLineService: ResearchLineService
) : BaseService<ResearchTask, Long>(researchTaskRepository) {

    suspend fun existsByIdAndUserId(id: Long, userId: Long): Boolean =
        researchTaskRepository.existsByIdAndUserId(id, userId)

    suspend fun createResearchTask(dto: ResearchTaskCreateDto, userId: Long): Long {
        // Проверяем владение исследованием
        val isOwner = researchLineService.existsByIdAndUserId(dto.researchLineId, userId)
        if (!isOwner) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to modify this research")
        }

        val task = ResearchTask(
            researchLineId = dto.researchLineId,
            userId = userId,
            title = dto.title,
            description = dto.description,
            status = dto.status
        )

        val savedTask = save(task)
        return savedTask.id!!
    }
}
