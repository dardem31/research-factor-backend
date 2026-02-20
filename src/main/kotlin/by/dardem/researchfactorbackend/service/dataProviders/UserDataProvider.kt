package by.dardem.researchfactorbackend.service.dataProviders

import by.dardem.researchfactorbackend.domain.dto.UserDto
import by.dardem.researchfactorbackend.service.domain.user.UserService
import by.dardem.researchfactorbackend.config.security.AuthenticatedUser
import org.springframework.stereotype.Service

@Service
class UserDataProvider(
    private val userService: UserService
) {

    suspend fun getUserData(userId: Long): UserDto? {
        val authenticatedUser = AuthenticatedUser.get()
        if (authenticatedUser != null && authenticatedUser.id == userId) {
            return authenticatedUser
        }

        return userService.readUser(userId)
    }

}