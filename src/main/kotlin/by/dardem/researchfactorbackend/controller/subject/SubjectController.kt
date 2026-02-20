package by.dardem.researchfactorbackend.controller.subject

import by.dardem.researchfactorbackend.config.security.AuthenticatedUser
import by.dardem.researchfactorbackend.domain.dto.subject.SubjectCreateDto
import by.dardem.researchfactorbackend.service.domain.subject.SubjectService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class SubjectController(
    private val subjectService: SubjectService
) {
    @PostMapping("/api/v1/dashboard/subject")
    suspend fun createSubject(
        @RequestBody dto: SubjectCreateDto,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): Map<String, Long> {
        val subjectId = subjectService.createSubject(dto, user.user.id)
        return mapOf("id" to subjectId)
    }
}
