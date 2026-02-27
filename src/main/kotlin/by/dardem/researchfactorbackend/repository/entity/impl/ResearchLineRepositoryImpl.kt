package by.dardem.researchfactorbackend.repository.entity.impl

import by.dardem.researchfactorbackend.domain.entity.research_line.ResearchLine
import by.dardem.researchfactorbackend.repository.base.AbstractReactiveCrudDao
import by.dardem.researchfactorbackend.repository.entity.ResearchLineRepository
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

    override suspend fun findByIdAndUserId(id: Long, userId: Long): ResearchLine? =
        find { root, builder ->
            mutableListOf(
                builder.equal(root.get<Long>("id"), id),
                builder.equal(root.get<Long>("userId"), userId)
            )
        }

    override suspend fun findAllByResearchIdAndUserId(researchId: Long, userId: Long): List<ResearchLine> =
        findList { root, builder ->
            mutableListOf(
                builder.equal(root.get<Long>("researchId"), researchId),
                builder.equal(root.get<Long>("userId"), userId)
            )
        }

    override suspend fun deleteByIdAndUserId(id: Long, userId: Long) {
        delete { root, builder ->
            mutableListOf(
                builder.equal(root.get<Long>("id"), id),
                builder.equal(root.get<Long>("userId"), userId)
            )
        }
    }
}
