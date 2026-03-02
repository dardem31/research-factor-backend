package by.dardem.researchfactorbackend.controller.research_line

import by.dardem.researchfactorbackend.config.security.AuthenticatedUser
import by.dardem.researchfactorbackend.domain.dto.research_line.task.ResearchTaskDto
import by.dardem.researchfactorbackend.service.domain.research_line.task.ResearchTaskService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class ResearchTaskController(
    private val researchTaskService: ResearchTaskService
) {

    @PostMapping("/api/v1/dashboard/research/research-lines/tasks")
    suspend fun createResearchTask(
        @RequestBody dto: ResearchTaskDto,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): ResponseEntity<ResearchTaskDto> {
        val result = researchTaskService.createResearchTask(dto, user.user.id)
        return ResponseEntity.ok(result)
    }

    @PutMapping("/api/v1/dashboard/research/research-lines/tasks")
    suspend fun updateResearchTask(
        @RequestBody dto: ResearchTaskDto,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): ResponseEntity<ResearchTaskDto> {
        val result = researchTaskService.updateResearchTask(dto, user.user.id)
        return ResponseEntity.ok(result)
    }

    @DeleteMapping("/api/v1/dashboard/research/research-lines/tasks/{id}")
    suspend fun deleteResearchTask(
        @PathVariable id: Long,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): ResponseEntity<Void> {
        researchTaskService.deleteResearchTask(id, user.user.id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/api/v1/dashboard/research/research-lines/tasks/{researchLineId}")
    suspend fun getResearchTasksByResearchLineId(
        @PathVariable researchLineId: Long,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): ResponseEntity<List<ResearchTaskDto>> {
        val result = researchTaskService.getResearchTasksByResearchLineId(researchLineId, user.user.id)
        return ResponseEntity.ok(result)
    }
}
