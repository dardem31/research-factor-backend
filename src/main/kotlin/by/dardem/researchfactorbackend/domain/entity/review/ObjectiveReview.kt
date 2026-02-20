package by.dardem.researchfactorbackend.domain.entity.review

import by.dardem.researchfactorbackend.domain.enums.ReviewVerdict
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "objective_reviews")
class ObjectiveReview(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "objective_id", nullable = false)
    var objectiveId: Long,

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
