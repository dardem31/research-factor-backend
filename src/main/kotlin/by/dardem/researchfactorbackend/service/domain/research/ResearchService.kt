package by.dardem.researchfactorbackend.service.domain.research

import by.dardem.researchfactorbackend.domain.dto.research.ResearchCreateDto
import by.dardem.researchfactorbackend.domain.entity.research.PrimaryOutcome
import by.dardem.researchfactorbackend.domain.entity.research.Research
import by.dardem.researchfactorbackend.domain.entity.research.StudyProtocol
import by.dardem.researchfactorbackend.domain.entity.research.TrackedParameter
import by.dardem.researchfactorbackend.repository.entity.ResearchRepository
import by.dardem.researchfactorbackend.service.domain.base.BaseService
import org.springframework.stereotype.Service

@Service
class ResearchService(
    private val researchRepository: ResearchRepository
) : BaseService<Research, Long>(researchRepository) {

    suspend fun existsByIdAndUserId(id: Long, userId: Long): Boolean =
        researchRepository.existsByIdAndUserId(id, userId)

    suspend fun createResearch(dto: ResearchCreateDto, userId: Long): Long {
        val research = Research(
            title = dto.title,
            userId = userId,
            hypothesis = dto.hypothesis,
            description = dto.description
        )

        // Маппим протокол
        val protocol = StudyProtocol(
            research = research,
            primaryOutcome = dto.protocol.primaryOutcome,
            sampleSizeJustification = dto.protocol.sampleSizeJustification,
            statisticalMethod = dto.protocol.statisticalMethod,
            randomizationMethod = dto.protocol.randomizationMethod,
            blindingDetails = dto.protocol.blindingDetails,
            interventionDescription = dto.protocol.interventionDescription,
            inclusionCriteria = dto.protocol.inclusionCriteria,
            exclusionCriteria = dto.protocol.exclusionCriteria,
            earlyStoppingCriteria = dto.protocol.earlyStoppingCriteria
        )
        research.protocol = protocol

        // Маппим исходы
        research.primaryOutcomes = dto.primaryOutcomes.map {
            PrimaryOutcome(research = research, text = it.text)
        }.toMutableList()

        // Маппим параметры
        research.trackedParameters = dto.trackedParameters.map {
            TrackedParameter(
                research = research,
                name = it.name,
                unit = it.unit
            )
        }.toMutableList()

        val savedResearch = save(research)
        return savedResearch.id!!
    }
}
