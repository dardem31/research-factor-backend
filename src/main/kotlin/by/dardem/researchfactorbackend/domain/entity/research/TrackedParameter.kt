package by.dardem.researchfactorbackend.domain.entity.research

import jakarta.persistence.*

@Entity
@Table(name = "tracked_parameters")
class TrackedParameter(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "research_id", nullable = false)
    var research: Research? = null,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var unit: String
)
