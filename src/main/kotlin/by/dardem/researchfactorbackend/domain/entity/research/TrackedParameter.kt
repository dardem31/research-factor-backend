package by.dardem.researchfactorbackend.domain.entity.research

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "tracked_parameters")
class TrackedParameter(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "research_id", nullable = false)
    val research: Research,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var unit: String,

    @Column(name = "reference_min")
    var referenceMin: BigDecimal? = null,

    @Column(name = "reference_max")
    var referenceMax: BigDecimal? = null
)
