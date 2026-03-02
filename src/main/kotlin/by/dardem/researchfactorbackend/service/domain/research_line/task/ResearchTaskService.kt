package by.dardem.researchfactorbackend.service.domain.research_line.task

import by.dardem.researchfactorbackend.domain.dto.mappers.ResearchTaskMapper
import by.dardem.researchfactorbackend.domain.dto.research_line.task.ResearchTaskDto
import by.dardem.researchfactorbackend.domain.entity.research_line.ResearchTask
import by.dardem.researchfactorbackend.repository.entity.ResearchTaskRepository
import by.dardem.researchfactorbackend.service.domain.base.BaseService
import by.dardem.researchfactorbackend.service.domain.research_line.ResearchLineService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ResearchTaskService(
    private val researchTaskRepository: ResearchTaskRepository,
    private val researchLineService: ResearchLineService,
    private val researchTaskMapper: ResearchTaskMapper
) : BaseService<ResearchTask, Long>(researchTaskRepository) {

    suspend fun existsByIdAndUserId(id: Long, userId: Long): Boolean =
        researchTaskRepository.existsByIdAndUserId(id, userId)

    suspend fun createResearchTask(dto: ResearchTaskDto, userId: Long): ResearchTaskDto {
        researchLineService.validateResearchLineOwnership(dto.researchLineId, userId)

        val task = researchTaskMapper.toEntity(dto).apply {
            this.userId = userId
        }

        val savedTask = save(task)
        return researchTaskMapper.toDto(savedTask)
    }

    suspend fun updateResearchTask(dto: ResearchTaskDto, userId: Long): ResearchTaskDto {
        val id = dto.id ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Research task id is required")

        val task = researchTaskRepository.findByIdAndUserId(id, userId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Research task not found")

        researchTaskMapper.updateEntityFromDto(dto, task)
        val savedTask = saveOrUpdate(task)
        return researchTaskMapper.toDto(savedTask)
    }

    suspend fun deleteResearchTask(id: Long, userId: Long) {
        val exists = researchTaskRepository.existsByIdAndUserId(id, userId)
        if (!exists) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Research task not found")
        }
        researchTaskRepository.deleteByIdAndUserId(id, userId)
    }

    suspend fun getResearchTasksByResearchLineId(researchLineId: Long, userId: Long): List<ResearchTaskDto> {
        researchLineService.validateResearchLineOwnership(researchLineId, userId)
        return researchTaskRepository.findAllByResearchLineIdAndUserId(researchLineId, userId)
            .map(researchTaskMapper::toDto)
    }

    suspend fun validateTaskOwnership(taskId: Long, userId: Long) {
        val isOwner = researchTaskRepository.existsByIdAndUserId(taskId, userId)
        if (!isOwner) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Research task not found or you don't have permission")
        }
    }
}
