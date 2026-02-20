package by.dardem.researchfactorbackend.domain.entity.research

import by.dardem.researchfactorbackend.domain.enums.BlindingType
import by.dardem.researchfactorbackend.domain.enums.ResearchStatus
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

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
    var trackedParameters: MutableList<TrackedParameter> = mutableListOf(),

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant? = null,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant? = null
)


