package by.dardem.researchfactorbackend.service.domain.research.search

import by.dardem.researchfactorbackend.domain.dto.filter.ResearchFilterDto
import by.dardem.researchfactorbackend.domain.dto.view.ResearchOverviewItem
import by.dardem.researchfactorbackend.domain.enums.ResearchStatus
import by.dardem.researchfactorbackend.domain.util.SearchResultDto
import by.dardem.researchfactorbackend.repository.base.CriteriaFunction
import by.dardem.researchfactorbackend.repository.view.ResearchOverviewItemRepository
import by.dardem.researchfactorbackend.service.domain.base.BaseSearchService
import jakarta.persistence.criteria.Predicate
import org.springframework.stereotype.Service

@Service
class ResearchOverviewItemService(
    researchOverviewItemRepository: ResearchOverviewItemRepository
) : BaseSearchService<ResearchOverviewItem>(researchOverviewItemRepository) {
    suspend fun list(
        researchFilterDto: ResearchFilterDto,
        userId: Long
    ): SearchResultDto<ResearchOverviewItem> {
        val criteria = getCriteria(researchFilterDto, userId)
        val result = list(researchFilterDto, criteria)
        return result
    }
    suspend fun count(
        researchFilterDto: ResearchFilterDto,
        ownerId: Long,
    ): Long {
        return count(getCriteria(researchFilterDto, ownerId))
    }

    private suspend fun getCriteria(
        researchFilterDto: ResearchFilterDto,
        userId: Long
    ): CriteriaFunction<ResearchOverviewItem> {
        return CriteriaFunction { root, builder ->
            val predicates: MutableList<Predicate> = ArrayList()
            val filter = researchFilterDto.filter
            predicates.add(builder.equal(root.get<Long>("userId"), userId))
            if (filter != null) {
                filter.title?.let { predicates.add(builder.equal(root.get<String>("title"), it)) }
                filter.status?.let { predicates.add(builder.equal(root.get<ResearchStatus>("status"), it)) }
            }
            predicates
        }
    }
}