package by.dardem.researchfactorbackend.controller

import by.dardem.researchfactorbackend.domain.dto.LoginRequest
import by.dardem.researchfactorbackend.service.infrustructure.jwt.JwtService
import by.dardem.researchfactorbackend.service.domain.user.UserService
import by.dardem.researchfactorbackend.service.dataProviders.UserDataProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    @Value("\${spring.profiles.active}")
    private val activeProfile: String
) {

    @Value("\${jwt.cookie-name}")
    lateinit var tokenCookieName: String

    @PostMapping("/login")
    suspend fun login(@RequestBody request: LoginRequest, response: ServerHttpResponse): ResponseEntity<Void> {
        val user = userService.findByEmail(request.username)
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password")

        if (!passwordEncoder.matches(request.password, user.passwordHash)) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password")
        }

        val token = jwtService.generateToken(user.id!!)

        // Создаём httpOnly cookie
        val cookie = ResponseCookie.from(tokenCookieName, token)
            .httpOnly(true)
            .path("/")
            .maxAge(jwtService.ttlSeconds)
            .apply {
                if (activeProfile == "dev") {
                    sameSite("Lax")
                } else {
                    sameSite("None")
                    secure(true)
                }
            }
            .build()

        response.addCookie(cookie)

        return ResponseEntity.ok().build()
    }
}
