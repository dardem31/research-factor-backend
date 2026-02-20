package by.dardem.researchfactorbackend.domain.entity.research_line

import by.dardem.researchfactorbackend.domain.enums.ResearchLineStatus
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "research_lines")
class ResearchLine(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column("user_id")
    val userId: Long,

    @Column(name = "research_id", nullable = false)
    var researchId: Long,

    @Column(name = "sequence_order", nullable = false)
    var sequenceOrder: Int,

    @Column(nullable = false)
    var title: String,

    @Column
    var duration: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: ResearchLineStatus = ResearchLineStatus.LOCKED,

    @Column(name = "planned_start_date")
    var plannedStartDate: Instant? = null,

    @Column(name = "planned_end_date")
    var plannedEndDate: Instant? = null,

    @Column(name = "actual_start_date")
    var actualStartDate: Instant? = null,

    @Column(name = "actual_end_date")
    var actualEndDate: Instant? = null
)

