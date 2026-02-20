package by.dardem.researchfactorbackend.domain.dto

import java.time.Instant

data class JwtPayload(
    val userId: Long,
    val issuedAt: Instant,
    val expiresAt: Instant
)
