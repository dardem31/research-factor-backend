package by.dardem.researchfactorbackend.controller.research

import by.dardem.researchfactorbackend.config.security.AuthenticatedUser
import by.dardem.researchfactorbackend.domain.dto.filter.ResearchFilterDto
import by.dardem.researchfactorbackend.domain.dto.research.ResearchDto
import by.dardem.researchfactorbackend.domain.dto.view.ResearchOverviewItem
import by.dardem.researchfactorbackend.domain.util.CountSearchResultDto
import by.dardem.researchfactorbackend.domain.util.SearchResultDto
import by.dardem.researchfactorbackend.service.domain.research.ResearchService
import by.dardem.researchfactorbackend.service.domain.research.search.ResearchOverviewItemService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class ResearchController(
    private val researchService: ResearchService,
    private val researchOverviewItemService: ResearchOverviewItemService
) {

    @GetMapping("/api/v1/dashboard/research/{id}")
    suspend fun getResearch(
        @PathVariable id: Long,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): ResponseEntity<ResearchDto> {
        val result = researchService.getResearch(id, user.user.id)
        return ResponseEntity.ok(result)
    }
    @PostMapping("/api/v1/dashboard/research")
    suspend fun createResearch(
        @RequestBody dto: ResearchDto,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): ResponseEntity<ResearchDto> {
        val result = researchService.createResearch(dto, user.user.id)
        return ResponseEntity.ok(result)
    }
    @PutMapping("/api/v1/dashboard/research")
    suspend fun updateResearch(
        @RequestBody dto: ResearchDto,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): ResponseEntity<ResearchDto> {
        val result = researchService.updateResearch(dto, user.user.id)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/api/v1/dashboard/research/list")
    suspend fun list(
        @ModelAttribute filterDto: ResearchFilterDto,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): ResponseEntity<SearchResultDto<ResearchOverviewItem>> =
        ResponseEntity.ok(researchOverviewItemService.list(filterDto, user.user.id))

    @GetMapping("/api/v1/dashboard/research/count")
    suspend fun count(
        @ModelAttribute listFilter: ResearchFilterDto,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): ResponseEntity<CountSearchResultDto> =
        CountSearchResultDto.okResponse(researchOverviewItemService.count(
            listFilter, user.user.id))

}
