package by.dardem.researchfactorbackend.domain.entity.audit

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "audit_entries")
class AuditEntry(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "research_id", nullable = false)
    var researchId: Long,

    @Column(name = "actor_id", nullable = false)
    var actorId: Long,

    @Column(name = "actor_role", nullable = false)
    var actorRole: String,

    @Column(nullable = false)
    var action: String,

    @Column(columnDefinition = "jsonb")
    var payload: String? = null,

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant = Instant.now()
)
