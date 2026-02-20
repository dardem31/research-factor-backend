package by.dardem.researchfactorbackend.repository.impl

import by.dardem.researchfactorbackend.domain.entity.research_line.Artifact
import by.dardem.researchfactorbackend.repository.ArtifactRepository
import by.dardem.researchfactorbackend.repository.base.AbstractReactiveCrudDao
import org.hibernate.reactive.mutiny.Mutiny
import org.springframework.stereotype.Repository

@Repository
class ArtifactRepositoryImpl(
    sessionFactory: Mutiny.SessionFactory
) : AbstractReactiveCrudDao<Artifact, Long>(Artifact::class.java, sessionFactory),
    ArtifactRepository {
    override suspend fun existsByIdAndUserId(id: Long, userId: Long): Boolean =
        exists { root, builder ->
            mutableListOf(
                builder.equal(root.get<Long>("id"), id),
                builder.equal(root.get<Long>("userId"), userId)
            )
        }
}
