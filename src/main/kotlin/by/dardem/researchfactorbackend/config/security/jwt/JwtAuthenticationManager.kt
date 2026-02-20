package by.dardem.researchfactorbackend.config.security.jwt

import by.dardem.researchfactorbackend.config.security.AuthenticatedUser
import by.dardem.researchfactorbackend.service.JwtService
import by.dardem.researchfactorbackend.service.dataProviders.UserDataProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class JwtAuthenticationManager(
    private val jwtService: JwtService,
    private val userDataProvider: UserDataProvider
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val token = authentication.credentials as String

        return Mono.fromCallable { jwtService.extractUserId(token) }
            .flatMap { userId ->
                kotlinx.coroutines.reactor.mono {
                    userDataProvider.getUserData(userId)
                }.flatMap { user ->
                    if (user == null) {
                        Mono.error(BadCredentialsException("Invalid JWT"))
                    } else {
                        val principal = AuthenticatedUser.build(user)
                        Mono.just<Authentication>(
                            UsernamePasswordAuthenticationToken(
                                principal,
                                token,
                                principal.authorities
                            )
                        )
                    }
                }
            }
    }
}
