package by.dardem.researchfactorbackend.controller.research_line

import by.dardem.researchfactorbackend.config.security.AuthenticatedUser
import by.dardem.researchfactorbackend.domain.dto.research_line.StageQuestionDto
import by.dardem.researchfactorbackend.service.domain.research_line.StageQuestionService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class StageQuestionController(
    private val stageQuestionService: StageQuestionService
) {

    @PostMapping("/api/v1/dashboard/research/research-lines/stage-questions")
    suspend fun createStageQuestion(
        @RequestBody dto: StageQuestionDto,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): ResponseEntity<StageQuestionDto> {
        val result = stageQuestionService.createStageQuestion(dto, user.user.id)
        return ResponseEntity.ok(result)
    }

    @PutMapping("/api/v1/dashboard/research/research-lines/stage-questions")
    suspend fun updateStageQuestion(
        @RequestBody dto: StageQuestionDto,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): ResponseEntity<StageQuestionDto> {
        val result = stageQuestionService.updateStageQuestion(dto, user.user.id)
        return ResponseEntity.ok(result)
    }

    @DeleteMapping("/api/v1/dashboard/research/research-lines/stage-questions/{id}")
    suspend fun deleteStageQuestion(
        @PathVariable id: Long,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): ResponseEntity<Void> {
        stageQuestionService.deleteStageQuestion(id, user.user.id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/api/v1/dashboard/research/research-lines/stage-questions/{researchLineId}")
    suspend fun getStageQuestionsByResearchLineId(
        @PathVariable researchLineId: Long,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): ResponseEntity<List<StageQuestionDto>> {
        val result = stageQuestionService.getStageQuestionsByResearchLineId(researchLineId, user.user.id)
        return ResponseEntity.ok(result)
    }
}
