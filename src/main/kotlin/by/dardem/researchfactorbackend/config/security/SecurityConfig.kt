package by.dardem.researchfactorbackend.config.security

import by.dardem.researchfactorbackend.config.security.filters.AccessLogFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import org.springframework.web.cors.CorsConfiguration
import reactor.core.publisher.Mono

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    private val jwtAuthenticationWebFilter: AuthenticationWebFilter,
    private val accessLogFilter: AccessLogFilter
) {

    @Bean
    fun filterChain(http: ServerHttpSecurity): SecurityWebFilterChain {

        http
            .csrf { it.disable() }
            .cors {
                it.configurationSource {
                    val cors = CorsConfiguration()
                    cors.allowedOriginPatterns = listOf("*")
                    cors.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                    cors.allowedHeaders = listOf("*")
                    cors.allowCredentials = true
                    cors
                }
            }
            .authorizeExchange {
                it.pathMatchers("/api/v1/dashboard/**").authenticated()
                    .anyExchange().permitAll()
            }
            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
            .exceptionHandling {
                it.authenticationEntryPoint { exchange, _ ->
                    val response = exchange.response
                    response.statusCode = HttpStatus.UNAUTHORIZED
                    response.headers.contentType = MediaType.APPLICATION_JSON
                    val body = """{"code":401,"message":"Unauthorized"}"""
                    val buffer = response.bufferFactory().wrap(body.toByteArray())
                    response.writeWith(Mono.just(buffer))
                }
            }

        http.addFilterAt(accessLogFilter, SecurityWebFiltersOrder.FIRST)
        http.addFilterAt(jwtAuthenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)

        return http.build()
    }
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

}

//fun main() {
//    val encoder = BCryptPasswordEncoder()
//    val rawPassword = "password123"
//    val hash = encoder.encode(rawPassword)
//    println(hash)
//}