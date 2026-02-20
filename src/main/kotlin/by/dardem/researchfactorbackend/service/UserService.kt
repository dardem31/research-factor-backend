package by.dardem.researchfactorbackend.service

import by.dardem.researchfactorbackend.domain.dto.UserDto
import by.dardem.researchfactorbackend.domain.dto.mappers.UserMapper
import by.dardem.researchfactorbackend.domain.entity.User
import by.dardem.researchfactorbackend.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) {
    suspend fun readUser(id: Long): UserDto? =
        userRepository.findById(id)?.let(userMapper::toDto)

    suspend fun findByEmail(email: String): User? = userRepository.findByEmail(email)
}