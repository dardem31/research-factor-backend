package by.dardem.researchfactorbackend.repository.subject.impl

import by.dardem.researchfactorbackend.domain.entity.subject.Subject
import by.dardem.researchfactorbackend.repository.subject.SubjectRepository
import by.dardem.researchfactorbackend.repository.base.AbstractReactiveCrudDao
import org.hibernate.reactive.mutiny.Mutiny
import org.springframework.stereotype.Repository

@Repository
class SubjectRepositoryImpl(
    sessionFactory: Mutiny.SessionFactory
) : AbstractReactiveCrudDao<Subject, Long>(Subject::class.java, sessionFactory),
    SubjectRepository
