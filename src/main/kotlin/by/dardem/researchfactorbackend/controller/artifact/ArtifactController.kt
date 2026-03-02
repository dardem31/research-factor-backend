package by.dardem.researchfactorbackend.controller.artifact

import by.dardem.researchfactorbackend.config.security.AuthenticatedUser
import by.dardem.researchfactorbackend.domain.dto.research_line.artifact.ArtifactDto
import by.dardem.researchfactorbackend.service.domain.artifact.ArtifactService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class ArtifactController(
    private val artifactService: ArtifactService
) {

    @PostMapping("/api/v1/dashboard/research/research-line/task/artifact")
    suspend fun createArtifact(
        @RequestBody dto: ArtifactDto,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): ResponseEntity<ArtifactDto> {
        val result = artifactService.createArtifact(dto, user.user.id)
        return ResponseEntity.ok(result)
    }

    @PutMapping("/api/v1/dashboard/research/research-line/task/artifact")
    suspend fun updateArtifact(
        @RequestBody dto: ArtifactDto,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): ResponseEntity<ArtifactDto> {
        val result = artifactService.updateArtifact(dto, user.user.id)
        return ResponseEntity.ok(result)
    }

    @DeleteMapping("/api/v1/dashboard/research/research-line/task/artifact/{id}")
    suspend fun deleteArtifact(
        @PathVariable id: Long,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): ResponseEntity<Void> {
        artifactService.deleteArtifact(id, user.user.id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/api/v1/dashboard/research/research-line/task/artifact/{taskId}")
    suspend fun getArtifactsByTaskId(
        @PathVariable taskId: Long,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): ResponseEntity<List<ArtifactDto>> {
        val result = artifactService.getArtifactsByTaskId(taskId, user.user.id)
        return ResponseEntity.ok(result)
    }
}
