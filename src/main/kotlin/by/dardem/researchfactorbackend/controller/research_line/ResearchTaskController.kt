package by.dardem.researchfactorbackend.controller.research_line

import by.dardem.researchfactorbackend.config.security.AuthenticatedUser
import by.dardem.researchfactorbackend.domain.dto.research_line.task.ResearchTaskDto
import by.dardem.researchfactorbackend.service.domain.research_line.task.ResearchTaskService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ResearchTaskController(
    private val researchTaskService: ResearchTaskService
) {

    @PostMapping("/api/v1/dashboard/research-tasks")
    suspend fun createResearchTask(
        @RequestBody dto: ResearchTaskDto,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): Map<String, Long> {
        val taskId = researchTaskService.createResearchTask(dto, user.user.id)
        return mapOf("id" to taskId)
    }
}
