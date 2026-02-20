package by.dardem.researchfactorbackend.repository.research.impl

import by.dardem.researchfactorbackend.domain.entity.research.Research
import by.dardem.researchfactorbackend.repository.research.ResearchRepository
import by.dardem.researchfactorbackend.repository.base.AbstractReactiveCrudDao
import org.hibernate.reactive.mutiny.Mutiny
import org.springframework.stereotype.Repository

@Repository
class ResearchRepositoryImpl(
    sessionFactory: Mutiny.SessionFactory
) : AbstractReactiveCrudDao<Research, Long>(Research::class.java, sessionFactory),
    ResearchRepository {
    override suspend fun existsByIdAndUserId(id: Long, userId: Long): Boolean =
        exists { root, builder ->
            mutableListOf(
                builder.equal(root.get<Long>("id"), id),
                builder.equal(root.get<Long>("userId"), userId)
            )
        }
}
