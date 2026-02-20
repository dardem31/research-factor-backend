package by.dardem.researchfactorbackend.domain.entity.subject

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "parameter_changes")
class ParameterChange(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "subject_update_id", nullable = false)
    var subjectUpdateId: Long,

    @Column(name = "parameter_id", nullable = false)
    var parameterId: Long,

    @Column(name = "previous_value")
    var previousValue: BigDecimal? = null,

    @Column(name = "new_value", nullable = false)
    var newValue: BigDecimal
)
