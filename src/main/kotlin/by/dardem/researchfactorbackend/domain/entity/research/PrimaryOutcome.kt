package by.dardem.researchfactorbackend.domain.entity.research

import jakarta.persistence.*

@Entity
@Table(name = "primary_outcomes")
class PrimaryOutcome(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "research_id", nullable = false)
    var research: Research? = null,

    @Column
    var text: String? = null,

    @Column
    var status: String? = null
)
