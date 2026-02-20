package by.dardem.researchfactorbackend.repository.impl

import by.dardem.researchfactorbackend.domain.entity.User
import by.dardem.researchfactorbackend.repository.UserRepository
import by.dardem.researchfactorbackend.repository.base.AbstractReactiveCrudDao
import org.hibernate.reactive.mutiny.Mutiny
import org.springframework.stereotype.Repository

@Repository
class UserDaoImpl(
    sessionFactory: Mutiny.SessionFactory
) : AbstractReactiveCrudDao<User, Long>(User::class.java, sessionFactory),
    UserRepository {
    override suspend fun findByEmail(email: String): User? = find { root, builder ->
        mutableListOf(
            builder.equal(root.get<String>("email"), email)
        )
    }
}