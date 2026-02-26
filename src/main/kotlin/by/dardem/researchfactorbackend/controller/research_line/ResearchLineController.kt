package by.dardem.researchfactorbackend.controller.research_line

import by.dardem.researchfactorbackend.config.security.AuthenticatedUser
import by.dardem.researchfactorbackend.domain.dto.research_line.ResearchLineDto
import by.dardem.researchfactorbackend.service.domain.research_line.ResearchLineService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ResearchLineController(
    private val researchLineService: ResearchLineService
) {

    @PostMapping("/api/v1/dashboard/research/research-lines")
    suspend fun createResearchLine(
        @RequestBody dto: ResearchLineDto,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): Map<String, Long> {
        val lineId = researchLineService.createResearchLine(dto, user.user.id)
        return mapOf("id" to lineId)
    }
}
