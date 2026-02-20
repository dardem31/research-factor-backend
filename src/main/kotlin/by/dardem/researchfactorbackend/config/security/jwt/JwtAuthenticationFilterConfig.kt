package by.dardem.researchfactorbackend.config.security.jwt

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.web.server.authentication.AuthenticationWebFilter

@Configuration
class JwtAuthenticationFilterConfig {

    @Bean
    fun jwtAuthenticationWebFilter(
        authenticationManager: JwtAuthenticationManager,
        authenticationConverter: JwtServerAuthenticationConverter
    ): AuthenticationWebFilter {

        val filter = AuthenticationWebFilter(authenticationManager)
        filter.setServerAuthenticationConverter(authenticationConverter)

        return filter
    }
}
