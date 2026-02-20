package by.dardem.researchfactorbackend.domain.entity.review

import by.dardem.researchfactorbackend.domain.enums.ReviewVerdict
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "report_reviews")
class ResearchReportReview(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "report_id", nullable = false)
    var reportId: Long,

    @Column(name = "reviewer_id", nullable = false)
    var reviewerId: Long,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var verdict: ReviewVerdict,

    @Column(nullable = false)
    var comment: String,

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant = Instant.now()
)
