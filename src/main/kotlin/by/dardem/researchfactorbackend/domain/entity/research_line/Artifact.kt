package by.dardem.researchfactorbackend.domain.entity.research_line

import by.dardem.researchfactorbackend.domain.enums.ArtifactType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "artifacts")
class Artifact(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column("user_id")
    val userId: Long,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var type: ArtifactType,

    @Column(name = "storage_url", nullable = false)
    var storageUrl: String,

    @Column(nullable = false)
    var sha256: String,

    @Column(columnDefinition = "jsonb")
    var metadata: String? = null,

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant = Instant.now()
)