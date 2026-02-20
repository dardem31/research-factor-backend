package by.dardem.researchfactorbackend.service.domain.research_line.task

import by.dardem.researchfactorbackend.domain.dto.execution.LogEntryCreateDto
import by.dardem.researchfactorbackend.domain.entity.research_line.LogEntry
import by.dardem.researchfactorbackend.domain.entity.subject.ParameterChange
import by.dardem.researchfactorbackend.domain.entity.subject.SubjectUpdate
import by.dardem.researchfactorbackend.repository.execution.LogEntryRepository
import by.dardem.researchfactorbackend.service.base.BaseService
import by.dardem.researchfactorbackend.service.domain.artifact.ArtifactService
import by.dardem.researchfactorbackend.service.domain.base.BaseService
import by.dardem.researchfactorbackend.service.domain.research.ResearchService
import by.dardem.researchfactorbackend.service.domain.research_line.ResearchLineService
import by.dardem.researchfactorbackend.service.domain.subject.SubjectInternalService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class LogEntryService(
    private val logEntryRepository: LogEntryRepository,
    private val researchTaskService: ResearchTaskService,
    private val researchLineService: ResearchLineService,
    private val researchService: ResearchService,
    private val subjectInternalService: SubjectInternalService,
    private val artifactService: ArtifactService
) : BaseService<LogEntry, Long>(logEntryRepository) {

    suspend fun createLogEntry(dto: LogEntryCreateDto, userId: Long): Long {
        // 1. Проверяем таску и владение ею
        val lineId = researchTaskService.getResearchLineIdById(dto.taskId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found")
        val researchId = researchLineService.getResearchIdById(lineId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Research line not found")
        
        if (!researchService.existsByIdAndUserId(researchId, userId)) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Permission denied for task")
        }

        // 2. Проверяем артефакты (если есть) на корректность владельца
        if (dto.artifactIds.isNotEmpty()) {
            val artifacts = artifactService.findAllById(dto.artifactIds)
            if (artifacts.size != dto.artifactIds.size || artifacts.any { it.userId != userId }) {
                throw ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid artifacts or permission denied")
            }
        }

        // 3. Проверяем каждого сабъекта на корректность владельца
        for (updateDto in dto.subjectUpdates) {
            val subjectId = updateDto.subjectId
            if (subjectInternalService.getUserIdById(subjectId) != userId) {
                throw ResponseStatusException(HttpStatus.FORBIDDEN, "Permission denied for subject $subjectId")
            }
        }

        // 4. Собираем LogEntry
        val logEntry = LogEntry(
            taskId = dto.taskId,
            authorId = userId,
            text = dto.text,
            artifactIds = dto.artifactIds.toMutableList()
        )

        logEntry.subjectUpdates = dto.subjectUpdates.map { updateDto ->
            SubjectUpdate(
                subjectId = updateDto.subjectId,
                parameterChanges = updateDto.parameterChanges.map { changeDto ->
                    ParameterChange(
                        subjectUpdateId = 0, // Hibernate set it automatically
                        parameterId = changeDto.parameterId,
                        previousValue = changeDto.previousValue,
                        newValue = changeDto.newValue
                    )
                }.toMutableList()
            )
        }.toMutableList()

        val saved = save(logEntry)
        return saved.id!!
    }
}
