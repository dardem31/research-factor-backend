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
    
    override suspend fun findAllById(ids: List<Long>): List<Artifact> =
        if (ids.isEmpty()) emptyList() 
        else findList { root, builder ->
            mutableListOf(root.get<Long>("id").`in`(ids))
        }
}
