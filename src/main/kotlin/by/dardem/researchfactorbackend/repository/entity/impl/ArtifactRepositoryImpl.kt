package by.dardem.researchfactorbackend.repository.entity.impl

import by.dardem.researchfactorbackend.domain.entity.research_line.Artifact
import by.dardem.researchfactorbackend.repository.base.AbstractReactiveCrudDao
import by.dardem.researchfactorbackend.repository.entity.ArtifactRepository
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

    override suspend fun findByIdAndUserId(id: Long, userId: Long): Artifact? =
        find { root, builder ->
            mutableListOf(
                builder.equal(root.get<Long>("id"), id),
                builder.equal(root.get<Long>("userId"), userId)
            )
        }

    override suspend fun findAllByTaskIdAndUserId(taskId: Long, userId: Long): List<Artifact> =
        findList { root, builder ->
            mutableListOf(
                builder.equal(root.get<Long>("taskId"), taskId),
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
