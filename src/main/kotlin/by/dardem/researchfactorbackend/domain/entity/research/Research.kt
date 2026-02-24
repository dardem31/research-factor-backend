package by.dardem.researchfactorbackend.domain.entity.research

import by.dardem.researchfactorbackend.domain.enums.BlindingType
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
    val userId: Long,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var hypothesis: String,

    @Column(nullable = false)
    var description: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: ResearchStatus = ResearchStatus.DRAFT,

    @Enumerated(EnumType.STRING)
    @Column(name = "blinding_type", nullable = false)
    var blindingType: BlindingType = BlindingType.OPEN,

    @OneToOne(mappedBy = "research", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var protocol: StudyProtocol? = null,

    @OneToMany(mappedBy = "research", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var primaryOutcomes: MutableList<PrimaryOutcome> = mutableListOf(),

    @OneToMany(mappedBy = "research", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var trackedParameters: MutableList<TrackedParameter> = mutableListOf()
) : AuditableEntity()


