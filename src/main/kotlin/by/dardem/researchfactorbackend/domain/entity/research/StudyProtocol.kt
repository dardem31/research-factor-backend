package by.dardem.researchfactorbackend.domain.entity.research

import jakarta.persistence.*

@Entity
@Table(name = "study_protocols")
class StudyProtocol(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "research_id", nullable = false)
    var research: Research? = null,

    @Column(name = "primary_outcome", nullable = false)
    var primaryOutcome: String,

    @Column(name = "sample_size_justification", nullable = false)
    var sampleSizeJustification: String,

    @Column(name = "statistical_method", nullable = false)
    var statisticalMethod: String,

    @Column(name = "randomization_method", nullable = false)
    var randomizationMethod: String,

    @Column(name = "blinding_details", nullable = false)
    var blindingDetails: String,

    @Column(name = "intervention_description", nullable = false)
    var interventionDescription: String,

    @Column(name = "inclusion_criteria", nullable = false)
    var inclusionCriteria: String,

    @Column(name = "exclusion_criteria", nullable = false)
    var exclusionCriteria: String,

    @Column(name = "early_stopping_criteria", nullable = false)
    var earlyStoppingCriteria: String
)
