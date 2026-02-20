package by.dardem.researchfactorbackend.domain.dto

import java.time.Instant

data class UserDto(
    val id: Long,
    val email: String,
    val enabled: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant
)