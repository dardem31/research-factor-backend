package by.dardem.researchfactorbackend.config.security.jwt

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtServerAuthenticationConverter(

    @Value("\${jwt.cookie-name}")
    private val tokenHolderCookieName: String
) : ServerAuthenticationConverter {

    override fun convert(exchange: ServerWebExchange): Mono<Authentication> {
        val token = exchange.request.cookies[tokenHolderCookieName]
            ?.firstOrNull()
            ?.value

        return if (token.isNullOrBlank()) {
            Mono.empty()
        } else {
            Mono.just(JwtAuthenticationToken(token))
        }
    }
}
