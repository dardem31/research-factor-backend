package by.dardem.researchfactorbackend.domain.entity.research

import by.dardem.researchfactorbackend.domain.enums.ResearchStatus
import by.dardem.researchfactorbackend.domain.util.AuditableEntity
import jakarta.persistence.*

@Entity
@Table(name = "research")
class Research(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column("user_id")
    var userId: Long? = 0,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var hypothesis: String,

    @Column(nullable = false)
    var description: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: ResearchStatus? = ResearchStatus.DRAFT,
) : AuditableEntity() {
    @OneToOne(mappedBy = "research", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var protocol: StudyProtocol? = null
        set(value) {
            value?.let { it.research = this }
            field = value
        }

    @OneToMany(mappedBy = "research", cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    var primaryOutcomes: MutableList<PrimaryOutcome>? = mutableListOf()
        set(value) {
            value?.forEach { primaryOutcome -> primaryOutcome.research = this }
            field = value
        }

    @OneToMany(mappedBy = "research", cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    var trackedParameters: MutableList<TrackedParameter>? = mutableListOf()
        set(value) {
            value?.forEach { trackedParameter -> trackedParameter.research = this }
            field = value
        }
}


