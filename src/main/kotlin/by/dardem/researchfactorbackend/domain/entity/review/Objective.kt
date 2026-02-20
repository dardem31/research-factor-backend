package by.dardem.researchfactorbackend.domain.entity.review

import by.dardem.researchfactorbackend.domain.enums.ObjectiveStatus
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "objectives")
class Objective(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "research_line_id", nullable = false)
    var researchLineId: Long,

    @Column(nullable = false)
    var summary: String,

    @Column(nullable = false)
    var narrative: String,

    @Column(name = "protocol_deviations", nullable = false)
    var protocolDeviations: String,

    @Column(name = "adverse_events_summary", nullable = false)
    var adverseEventsSummary: String,

    @Column(name = "next_phase_recommendation")
    var nextPhaseRecommendation: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: ObjectiveStatus = ObjectiveStatus.PENDING,

    @Column(name = "submitted_at", nullable = false, updatable = false)
    var submittedAt: Instant = Instant.now()
)

