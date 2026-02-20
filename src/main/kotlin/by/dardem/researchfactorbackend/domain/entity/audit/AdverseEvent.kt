package by.dardem.researchfactorbackend.domain.entity.audit

import by.dardem.researchfactorbackend.domain.enums.AdverseEventSeverity
import jakarta.persistence.*
import java.time.LocalDate
import java.time.Instant

@Entity
@Table(name = "adverse_events")
class AdverseEvent(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "subject_id")
    var subjectId: Long? = null,

    @Column(name = "research_id", nullable = false)
    var researchId: Long,

    @Column(nullable = false)
    var description: String,

    @Column(name = "action_taken", nullable = false)
    var actionTaken: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var severity: AdverseEventSeverity,

    @Column(name = "occurred_at", nullable = false)
    var occurredAt: LocalDate,

    @Column(name = "related_to_intervention", nullable = false)
    var relatedToIntervention: Boolean,

    @Column(name = "reported_at", nullable = false, updatable = false)
    var reportedAt: Instant = Instant.now()
)
