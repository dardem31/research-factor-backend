package by.dardem.researchfactorbackend.service.domain.research

import by.dardem.researchfactorbackend.domain.dto.mappers.PrimaryOutcomeMapper
import by.dardem.researchfactorbackend.domain.dto.mappers.ProtocolMapper
import by.dardem.researchfactorbackend.domain.dto.mappers.ResearchMapper
import by.dardem.researchfactorbackend.domain.dto.mappers.TrackedParameterMapper
import by.dardem.researchfactorbackend.domain.dto.research.ResearchDto
import by.dardem.researchfactorbackend.domain.entity.research.Research
import by.dardem.researchfactorbackend.repository.entity.ResearchRepository
import by.dardem.researchfactorbackend.service.domain.base.BaseService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ResearchService(
    private val researchRepository: ResearchRepository,
    private val researchMapper: ResearchMapper,
    private val protocolMapper: ProtocolMapper,
    private val primaryOutcomeMapper: PrimaryOutcomeMapper,
    private val trackedParameterMapper: TrackedParameterMapper
) : BaseService<Research, Long>(researchRepository) {

    suspend fun existsByIdAndUserId(id: Long, userId: Long): Boolean =
        researchRepository.existsByIdAndUserId(id, userId)

    suspend fun createResearch(dto: ResearchDto, userId: Long): ResearchDto {
        val research = toEntity(dto)
        val savedResearch = save(research)
        return researchMapper.toDto(savedResearch)
    }

    suspend fun updateResearch(dto: ResearchDto, userId: Long): ResearchDto {
        val research = researchRepository.findByIdAndUserId(dto.id, userId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Research not found")

        researchMapper.updateEntityFromDto(dto, research)

        val existingOutcomesById = research.primaryOutcomes?.associateBy { it.id } ?: emptyMap()
        research.primaryOutcomes = dto.primaryOutcomes.map { outcomeDto ->
            existingOutcomesById[outcomeDto.id]?.apply {
                text = outcomeDto.text
            } ?: primaryOutcomeMapper.toEntity(outcomeDto)
        }.toMutableList()

        val existingParamsById = research.trackedParameters?.associateBy { it.id } ?: emptyMap()
        research.trackedParameters = dto.trackedParameters.map { paramDto ->
            existingParamsById[paramDto.id]?.apply {
                name = paramDto.name
                unit = paramDto.unit
            } ?: trackedParameterMapper.toEntity(paramDto)
        }.toMutableList()

        val savedResearch = saveOrUpdate(research)
        return researchMapper.toDto(savedResearch)
    }
    private fun toEntity(dto: ResearchDto): Research =
        researchMapper.toEntity(dto).apply {
            this.userId = userId
            protocol?.research = this
            primaryOutcomes?.forEach { it.research = this }
            trackedParameters?.forEach { it.research = this }
        }

    suspend fun getResearch(id: Long, userId: Long) =
        researchRepository.findByIdAndUserId(id, userId)?.let(researchMapper::toDto) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Research not found")
}
