package by.dardem.researchfactorbackend.config.security.jwt

import org.springframework.security.authentication.AbstractAuthenticationToken

class JwtAuthenticationToken(
    private val token: String
) : AbstractAuthenticationToken(emptyList()) {

    override fun getCredentials(): String = token
    override fun getPrincipal(): Any? = null
}
