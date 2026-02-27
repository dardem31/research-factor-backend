package by.dardem.researchfactorbackend.repository.entity.impl

import by.dardem.researchfactorbackend.domain.entity.research_line.StageQuestion
import by.dardem.researchfactorbackend.repository.base.AbstractReactiveCrudDao
import by.dardem.researchfactorbackend.repository.entity.StageQuestionRepository
import org.hibernate.reactive.mutiny.Mutiny
import org.springframework.stereotype.Repository

@Repository
class StageQuestionRepositoryImpl(
    sessionFactory: Mutiny.SessionFactory
) : AbstractReactiveCrudDao<StageQuestion, Long>(StageQuestion::class.java, sessionFactory),
    StageQuestionRepository {

    override suspend fun findAllByResearchLineId(researchLineId: Long): List<StageQuestion> =
        findList { root, builder ->
            mutableListOf(
                builder.equal(root.get<Long>("researchLineId"), researchLineId)
            )
        }

    override suspend fun deleteById(id: Long) {
        delete { root, builder ->
            mutableListOf(builder.equal(root.get<Long>("id"), id))
        }
    }
}
