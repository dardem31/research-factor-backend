package by.dardem.researchfactorbackend.domain.entity.subject

import by.dardem.researchfactorbackend.domain.entity.research.TrackedParameter
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Instant
import org.hibernate.annotations.UpdateTimestamp

@Entity
@Table(name = "parameter_fields")
class ParameterField(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    val subject: Subject,

    @Column(name = "parameter_id", nullable = false)
    val parameterId: Long,

    @Column(name = "current_value", nullable = false)
    var currentValue: BigDecimal,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant? = null
)
