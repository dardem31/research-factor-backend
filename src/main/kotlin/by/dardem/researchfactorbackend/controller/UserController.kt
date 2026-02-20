package by.dardem.researchfactorbackend.controller

import by.dardem.researchfactorbackend.config.security.AuthenticatedUser
import by.dardem.researchfactorbackend.domain.dto.UserDto
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    @GetMapping("/api/v1/dashboard/me")
    suspend fun home(@AuthenticationPrincipal user: AuthenticatedUser): ResponseEntity<UserDto> =
        ResponseEntity.ok(user.user)

}