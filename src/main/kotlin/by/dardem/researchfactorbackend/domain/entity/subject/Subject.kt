package by.dardem.researchfactorbackend.domain.entity.subject

import by.dardem.researchfactorbackend.domain.enums.SubjectStatus
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "subjects")
class Subject(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column("group_id")
    var groupId: Long,

    @Column("user_id")
    var userId: Long,

    @Column(nullable = false, unique = true)
    var code: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: SubjectStatus = SubjectStatus.ACTIVE,

    @Column(name = "withdrawal_reason")
    var withdrawalReason: String? = null,

    @Column(name = "withdrawal_date")
    var withdrawalDate: Instant? = null,

    @OneToMany(mappedBy = "subject", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var parameterFields: MutableList<ParameterField> = mutableListOf()
)
