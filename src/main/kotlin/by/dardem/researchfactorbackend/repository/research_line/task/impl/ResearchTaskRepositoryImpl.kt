package by.dardem.researchfactorbackend.repository.research_line.task.impl

import by.dardem.researchfactorbackend.domain.entity.research_line.ResearchTask
import by.dardem.researchfactorbackend.repository.research_line.task.ResearchTaskRepository
import by.dardem.researchfactorbackend.repository.base.AbstractReactiveCrudDao
import org.hibernate.reactive.mutiny.Mutiny
import org.springframework.stereotype.Repository

@Repository
class ResearchTaskRepositoryImpl(
    sessionFactory: Mutiny.SessionFactory
) : AbstractReactiveCrudDao<ResearchTask, Long>(ResearchTask::class.java, sessionFactory),
    ResearchTaskRepository {
    override suspend fun existsByIdAndUserId(id: Long, userId: Long): Boolean =
        exists { root, builder ->
            mutableListOf(
                builder.equal(root.get<Long>("id"), id),
                builder.equal(root.get<Long>("userId"), userId)
            )
        }
}
