package by.dardem.researchfactorbackend.repository.entity

import by.dardem.researchfactorbackend.domain.entity.User
import by.dardem.researchfactorbackend.repository.base.ReactiveCrudDao

interface UserRepository : ReactiveCrudDao<User, Long> {
    suspend fun findByEmail(email: String): User?
}