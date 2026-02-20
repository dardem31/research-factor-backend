package by.dardem.researchfactorbackend.controller.execution

import by.dardem.researchfactorbackend.config.security.AuthenticatedUser
import by.dardem.researchfactorbackend.domain.dto.execution.LogEntryCreateDto
import by.dardem.researchfactorbackend.service.domain.research_line.task.LogEntryService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/log-entries")
class LogEntryController(
    private val logEntryService: LogEntryService
) {

    @PostMapping
    suspend fun createLogEntry(
        @RequestBody dto: LogEntryCreateDto,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): Map<String, Long> {
        val id = logEntryService.createLogEntry(dto, user.user.id)
        return mapOf("id" to id)
    }
}
