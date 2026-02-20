package by.dardem.researchfactorbackend.domain.entity.subject

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "subject_updates")
class SubjectUpdate(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "log_entry_id", insertable = false, updatable = false)
    var logEntryId: Long? = null,

    @Column(name = "subject_id", nullable = false)
    var subjectId: Long,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "subject_update_id", nullable = false)
    var parameterChanges: MutableList<ParameterChange> = mutableListOf(),

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant = Instant.now()
)
