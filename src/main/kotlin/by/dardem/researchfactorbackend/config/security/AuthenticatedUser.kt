package by.dardem.researchfactorbackend.config.security

import by.dardem.researchfactorbackend.domain.dto.UserDto
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.slf4j.MDC
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

class AuthenticatedUser(
    val user: UserDto
) : UserDetails {
    var authorities: List<GrantedAuthority>? = null

    init {
        MDC.put("currentUser", "[userId=${user.id}]")
    }

    override fun getPassword(): String? = null

    override fun getUsername(): String = user.email

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities ?: emptyList()
    }

    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true

    companion object {
        fun build(
            user: UserDto
        ): AuthenticatedUser = AuthenticatedUser(user);

        suspend fun auditUsername(): String? =
            getAuthenticatedUserFromContext()?.username

        /**
         * Retrieves the authenticated user object from the security context, or null if not authenticated.
         */
        private suspend fun getAuthenticatedUserFromContext(): AuthenticatedUser? {
            val authentication: Authentication? = ReactiveSecurityContextHolder.getContext().awaitSingleOrNull()?.authentication
            return authentication?.principal as? AuthenticatedUser
        }
        suspend fun get(): UserDto? {
            val authenticatedUser = getAuthenticatedUserFromContext()
            return authenticatedUser?.user
        }

    }
}