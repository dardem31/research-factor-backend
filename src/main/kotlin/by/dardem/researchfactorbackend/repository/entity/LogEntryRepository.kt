package by.dardem.researchfactorbackend.repository.entity

import by.dardem.researchfactorbackend.domain.entity.research_line.LogEntry
import by.dardem.researchfactorbackend.repository.base.ReactiveCrudDao

interface LogEntryRepository : ReactiveCrudDao<LogEntry, Long>
