package by.dardem.researchfactorbackend.repository.subject.impl

import by.dardem.researchfactorbackend.domain.entity.subject.SubjectGroup
import by.dardem.researchfactorbackend.repository.subject.SubjectGroupRepository
import by.dardem.researchfactorbackend.repository.base.AbstractReactiveCrudDao
import org.hibernate.reactive.mutiny.Mutiny
import org.springframework.stereotype.Repository

@Repository
class SubjectGroupRepositoryImpl(
    sessionFactory: Mutiny.SessionFactory
) : AbstractReactiveCrudDao<SubjectGroup, Long>(SubjectGroup::class.java, sessionFactory),
    SubjectGroupRepository {

    override suspend fun existsByIdAndUserId(id: Long, userId: Long): Boolean =
        exists { root, builder ->
            mutableListOf(
                builder.equal(root.get<Long>("id"), id),
                builder.equal(root.get<Long>("userId"), userId)
            )
        }
}
