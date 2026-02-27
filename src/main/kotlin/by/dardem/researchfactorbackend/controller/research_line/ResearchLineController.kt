package by.dardem.researchfactorbackend.controller.research_line

import by.dardem.researchfactorbackend.config.security.AuthenticatedUser
import by.dardem.researchfactorbackend.domain.dto.research_line.ResearchLineDto
import by.dardem.researchfactorbackend.service.domain.research_line.ResearchLineService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class ResearchLineController(
    private val researchLineService: ResearchLineService
) {

    @PostMapping("/api/v1/dashboard/research/research-line")
    suspend fun createResearchLine(
        @RequestBody dto: ResearchLineDto,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): ResponseEntity<ResearchLineDto> {
        val result = researchLineService.createResearchLine(dto, user.user.id)
        return ResponseEntity.ok(result)
    }

    @PutMapping("/api/v1/dashboard/research/research-line")
    suspend fun updateResearchLine(
        @RequestBody dto: ResearchLineDto,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): ResponseEntity<ResearchLineDto> {
        val result = researchLineService.updateResearchLine(dto, user.user.id)
        return ResponseEntity.ok(result)
    }

    @DeleteMapping("/api/v1/dashboard/research/research-line/{id}")
    suspend fun deleteResearchLine(
        @PathVariable id: Long,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): ResponseEntity<Void> {
        researchLineService.deleteResearchLine(id, user.user.id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/api/v1/dashboard/research/research-line/{researchId}")
    suspend fun getResearchLinesByResearchId(
        @PathVariable researchId: Long,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): ResponseEntity<List<ResearchLineDto>> {
        val result = researchLineService.getResearchLinesByResearchId(researchId, user.user.id)
        return ResponseEntity.ok(result)
    }
}
