package by.dardem.researchfactorbackend.domain.dto.view

import by.dardem.researchfactorbackend.domain.enums.ResearchStatus
import jakarta.persistence.*
import org.hibernate.annotations.Immutable
import java.time.Instant

@Entity
@Immutable
@Table(name = "vw_research_overview_item")
class ResearchOverviewItem(
    @Id
    val id: Long,

    val title: String,

    @Column(name = "user_id")
    val userId: Long,

    val description: String?,

    @Enumerated(EnumType.STRING)
    val status: ResearchStatus,

    @Column(name = "research_lines_count")
    val researchLinesCount: Long,

    @Column(name = "primary_outcomes_count")
    val primaryOutcomesCount: Long,

    @Column(name = "created_at")
    val createdAt: Instant? = null
)
