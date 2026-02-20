package by.dardem.researchfactorbackend.domain.entity.subject

import jakarta.persistence.*

@Entity
@Table(name = "subject_groups")
class SubjectGroup(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column("research_id")
    val researchId: Long,

    @Column("user_id")
    val userId: Long,

    @Column(nullable = false)
    var label: String,

    @Column
    var description: String? = null,
)
