package by.dardem.researchfactorbackend.domain.entity.review

import by.dardem.researchfactorbackend.domain.enums.ObjectiveStatus
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "research_reports")
class ResearchReport(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "research_id", nullable = false)
    var researchId: Long,

    @Column(nullable = false)
    var summary: String,

    @Column(nullable = false)
    var narrative: String,

    @Column(name = "protocol_deviations", nullable = false)
    var protocolDeviations: String,

    @Column(name = "adverse_events", nullable = false)
    var adverseEvents: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: ObjectiveStatus = ObjectiveStatus.PENDING,

    @Column(name = "submitted_at", nullable = false, updatable = false)
    var submittedAt: Instant = Instant.now()
)
