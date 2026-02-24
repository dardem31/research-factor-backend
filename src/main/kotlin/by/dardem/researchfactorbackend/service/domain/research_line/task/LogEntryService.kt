package by.dardem.researchfactorbackend.service.domain.research_line.task

import by.dardem.researchfactorbackend.domain.dto.execution.LogEntryCreateDto
import by.dardem.researchfactorbackend.domain.entity.research_line.LogEntry
import by.dardem.researchfactorbackend.domain.entity.subject.ParameterChange
import by.dardem.researchfactorbackend.domain.entity.subject.SubjectUpdate
import by.dardem.researchfactorbackend.repository.entity.LogEntryRepository
import by.dardem.researchfactorbackend.service.domain.artifact.ArtifactService
import by.dardem.researchfactorbackend.service.domain.base.BaseService
import by.dardem.researchfactorbackend.service.domain.subject.SubjectService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class LogEntryService(
    private val logEntryRepository: LogEntryRepository,
    private val researchTaskService: ResearchTaskService,
    private val subjectService: SubjectService,
    private val artifactService: ArtifactService
) : BaseService<LogEntry, Long>(logEntryRepository) {

    suspend fun createLogEntry(dto: LogEntryCreateDto, userId: Long): Long {
        // 1. Проверяем таску и владение ею
        if (!researchTaskService.existsByIdAndUserId(dto.taskId, userId)) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Permission denied for task or task not found")
        }

        // 2. Проверяем артефакты (если есть) на корректность владельца
        if (dto.artifactIds.isNotEmpty()) {
            for (artifactId in dto.artifactIds) {
                if (!artifactService.existsByIdAndUserId(artifactId, userId)) {
                    throw ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid artifacts or permission denied")
                }
            }
        }

        // 3. Проверяем каждого сабъекта на корректность владельца
        for (updateDto in dto.subjectUpdates) {
            val subjectId = updateDto.subjectId
            if (!subjectService.existsByIdAndUserId(subjectId, userId)) {
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
            val subjectUpdate = SubjectUpdate(
                subjectId = updateDto.subjectId
            )
            subjectUpdate.parameterChanges = updateDto.parameterChanges.map { changeDto ->
                ParameterChange(
                    subjectUpdate = subjectUpdate,
                    parameterId = changeDto.parameterId,
                    previousValue = changeDto.previousValue,
                    newValue = changeDto.newValue
                )
            }.toMutableList()
            subjectUpdate
        }.toMutableList()

        val saved = save(logEntry)
        return saved.id!!
    }
}
