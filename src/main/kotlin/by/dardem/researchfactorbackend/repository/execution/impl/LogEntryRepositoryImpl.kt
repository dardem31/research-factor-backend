package by.dardem.researchfactorbackend.repository.execution.impl

import by.dardem.researchfactorbackend.domain.entity.research_line.LogEntry
import by.dardem.researchfactorbackend.repository.execution.LogEntryRepository
import by.dardem.researchfactorbackend.repository.base.AbstractReactiveCrudDao
import org.hibernate.reactive.mutiny.Mutiny
import org.springframework.stereotype.Repository

@Repository
class LogEntryRepositoryImpl(
    sessionFactory: Mutiny.SessionFactory
) : AbstractReactiveCrudDao<LogEntry, Long>(LogEntry::class.java, sessionFactory),
    LogEntryRepository
