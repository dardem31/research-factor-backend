package by.dardem.researchfactorbackend.domain.entity.research_line

import by.dardem.researchfactorbackend.domain.enums.ResearchTaskStatus
import jakarta.persistence.*

@Entity
@Table(name = "research_tasks")
class ResearchTask(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column("user_id")
    val userId: Long,

    @Column(name = "research_line_id", nullable = false)
    var researchLineId: Long,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var description: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: ResearchTaskStatus = ResearchTaskStatus.OPEN
)
