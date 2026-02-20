package by.dardem.researchfactorbackend.domain.entity.research_line

import by.dardem.researchfactorbackend.domain.entity.subject.SubjectUpdate
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "log_entries")
class LogEntry(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "task_id", nullable = false)
    var taskId: Long,

    @Column(name = "author_id", nullable = false)
    var authorId: Long,

    @Column(nullable = false)
    var text: String,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "log_entry_id", nullable = false)
    var subjectUpdates: MutableList<SubjectUpdate> = mutableListOf(),

    @ElementCollection
    @CollectionTable(
        name = "log_entry_artifacts",
        joinColumns = [JoinColumn(name = "log_entry_id")]
    )
    @Column(name = "artifact_id")
    var artifactIds: MutableList<Long> = mutableListOf(),

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant = Instant.now()
)
