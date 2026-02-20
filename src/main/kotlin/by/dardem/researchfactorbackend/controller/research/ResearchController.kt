package by.dardem.researchfactorbackend.controller.research

import by.dardem.researchfactorbackend.config.security.AuthenticatedUser
import by.dardem.researchfactorbackend.domain.dto.research.ResearchCreateDto
import by.dardem.researchfactorbackend.service.domain.research.ResearchService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class ResearchController(
    private val researchService: ResearchService
) {

    @PostMapping("/api/v1/dashboard/research")
    suspend fun createResearch(
        @RequestBody dto: ResearchCreateDto,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): Map<String, Long> {
        val researchId = researchService.createResearch(dto, user.user.id)
        return mapOf("id" to researchId)
    }
}
