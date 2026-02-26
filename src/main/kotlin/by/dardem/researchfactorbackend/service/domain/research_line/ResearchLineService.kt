package by.dardem.researchfactorbackend.service.domain.research_line

import by.dardem.researchfactorbackend.domain.dto.research_line.ResearchLineDto
import by.dardem.researchfactorbackend.domain.entity.research_line.ResearchLine
import by.dardem.researchfactorbackend.repository.entity.ResearchLineRepository
import by.dardem.researchfactorbackend.service.domain.base.BaseService
import by.dardem.researchfactorbackend.service.domain.research.ResearchService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ResearchLineService(
    private val researchLineRepository: ResearchLineRepository,
    private val researchService: ResearchService
) : BaseService<ResearchLine, Long>(researchLineRepository) {

    suspend fun createResearchLine(dto: ResearchLineDto, userId: Long): Long {
        // Проверка прав собственности на Research
        val isOwner = researchService.existsByIdAndUserId(dto.researchId, userId)

        if (!isOwner) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Research not found or you don't have permission")
        }

        val researchLine = ResearchLine(
            researchId = dto.researchId,
            userId = userId,
            sequenceOrder = dto.sequenceOrder,
            title = dto.title,
            duration = dto.duration,
            status = dto.status,
            plannedStartDate = dto.plannedStartDate,
            plannedEndDate = dto.plannedEndDate
        )

        val savedLine = save(researchLine)
        return savedLine.id!!
    }

    suspend fun existsByIdAndUserId(id: Long, userId: Long) =
        researchLineRepository.existsByIdAndUserId(id, userId)
}
