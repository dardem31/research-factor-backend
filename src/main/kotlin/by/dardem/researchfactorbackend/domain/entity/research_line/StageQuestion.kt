package by.dardem.researchfactorbackend.domain.entity.research_line

import jakarta.persistence.*

@Entity
@Table(name = "stage_questions")
class StageQuestion(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "research_line_id", nullable = false)
    var researchLineId: Long,

    @Column(nullable = false)
    var text: String,

    @Column(nullable = false)
    var status: String = "DRAFT"
)
