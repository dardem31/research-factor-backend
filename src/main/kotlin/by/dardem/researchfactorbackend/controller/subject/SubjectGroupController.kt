package by.dardem.researchfactorbackend.controller.subject

import by.dardem.researchfactorbackend.config.security.AuthenticatedUser
import by.dardem.researchfactorbackend.domain.dto.subject.SubjectGroupCreateDto
import by.dardem.researchfactorbackend.service.domain.subject.SubjectGroupService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SubjectGroupController(
    private val subjectGroupService: SubjectGroupService
) {

    @PostMapping("/api/v1/dashboard/subject-groups")
    suspend fun createSubjectGroup(
        @RequestBody dto: SubjectGroupCreateDto,
        @AuthenticationPrincipal user: AuthenticatedUser
    ): Map<String, Long> {
        val groupId = subjectGroupService.createSubjectGroup(dto, user.user.id)
        return mapOf("id" to groupId)
    }
}
