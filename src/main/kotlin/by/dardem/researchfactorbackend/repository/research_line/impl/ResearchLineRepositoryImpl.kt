package by.dardem.researchfactorbackend.repository.research_line.impl

import by.dardem.researchfactorbackend.domain.entity.research_line.ResearchLine
import by.dardem.researchfactorbackend.repository.research_line.ResearchLineRepository
import by.dardem.researchfactorbackend.repository.base.AbstractReactiveCrudDao
import org.hibernate.reactive.mutiny.Mutiny
import org.springframework.stereotype.Repository

@Repository
class ResearchLineRepositoryImpl(
    sessionFactory: Mutiny.SessionFactory
) : AbstractReactiveCrudDao<ResearchLine, Long>(ResearchLine::class.java, sessionFactory),
    ResearchLineRepository {
    override suspend fun existsByIdAndUserId(id: Long, userId: Long): Boolean =
        exists { root, builder ->
            mutableListOf(
                builder.equal(root.get<Long>("id"), id),
                builder.equal(root.get<Long>("userId"), userId)
            )
        }
}
