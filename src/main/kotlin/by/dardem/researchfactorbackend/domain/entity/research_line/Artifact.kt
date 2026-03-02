package by.dardem.researchfactorbackend.domain.entity.research_line

import by.dardem.researchfactorbackend.domain.enums.ArtifactType
import by.dardem.researchfactorbackend.domain.util.AuditableEntity
import jakarta.persistence.*

@Entity
@Table(name = "artifacts")
class Artifact(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column("user_id")
    var userId: Long? = 0,

    @Column("task_id")
    var taskId: Long? = 0,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var type: ArtifactType,

    @Column(name = "storage_url", nullable = false)
    var storageUrl: String? = null,

    @Column(nullable = false)
    var sha256: String? = null,

    @Column(columnDefinition = "jsonb")
    var metadata: String? = null,
) : AuditableEntity()