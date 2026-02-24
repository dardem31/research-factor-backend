package by.dardem.researchfactorbackend.repository.view.impl

import by.dardem.researchfactorbackend.domain.dto.view.ResearchOverviewItem
import by.dardem.researchfactorbackend.repository.base.view.SearchCriteriaResolverImpl
import by.dardem.researchfactorbackend.repository.view.ResearchOverviewItemRepository
import org.hibernate.reactive.mutiny.Mutiny
import org.springframework.stereotype.Repository

@Repository
class ResearchOverviewItemDaoImpl(
    sessionFactory: Mutiny.SessionFactory
) : SearchCriteriaResolverImpl<ResearchOverviewItem>(ResearchOverviewItem::class.java, sessionFactory),
    ResearchOverviewItemRepository