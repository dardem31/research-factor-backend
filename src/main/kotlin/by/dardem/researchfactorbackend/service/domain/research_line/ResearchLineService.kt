package by.dardem.researchfactorbackend.service.domain.research_line

import by.dardem.researchfactorbackend.domain.dto.mappers.ResearchLineMapper
import by.dardem.researchfactorbackend.domain.dto.research_line.ResearchLineDto
import by.dardem.researchfactorbackend.domain.entity.research_line.ResearchLine
import by.dardem.researchfactorbackend.domain.enums.ResearchLineStatus
import by.dardem.researchfactorbackend.repository.entity.ResearchLineRepository
import by.dardem.researchfactorbackend.service.domain.base.BaseService
import by.dardem.researchfactorbackend.service.domain.research.ResearchService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ResearchLineService(
    private val researchLineRepository: ResearchLineRepository,
    private val researchService: ResearchService,
    private val researchLineMapper: ResearchLineMapper
) : BaseService<ResearchLine, Long>(researchLineRepository) {

    suspend fun createResearchLine(dto: ResearchLineDto, userId: Long): ResearchLineDto {
        researchService.validateResearchOwnership(dto.researchId, userId)

        val researchLine = researchLineMapper.toEntity(dto).apply {
            this.userId = userId
            status = ResearchLineStatus.LOCKED
        }

        val savedLine = save(researchLine)
        return researchLineMapper.toDto(savedLine)
    }

    suspend fun updateResearchLine(dto: ResearchLineDto, userId: Long): ResearchLineDto {
        val researchLine = researchLineRepository.findByIdAndUserId(dto.id!!, userId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Research line not found")

        researchLineMapper.updateEntityFromDto(dto, researchLine)
        val savedLine = saveOrUpdate(researchLine)
        return researchLineMapper.toDto(savedLine)
    }

    suspend fun deleteResearchLine(id: Long, userId: Long) {
        validateResearchLineOwnership(id, userId)
        researchLineRepository.deleteByIdAndUserId(id, userId)
    }

    suspend fun getResearchLinesByResearchId(researchId: Long, userId: Long): List<ResearchLineDto> {
        researchService.validateResearchOwnership(researchId, userId)
        return researchLineRepository.findAllByResearchIdAndUserId(researchId, userId)
            .map(researchLineMapper::toDto)
    }

    suspend fun existsByIdAndUserId(id: Long, userId: Long) =
        researchLineRepository.existsByIdAndUserId(id, userId)

    private suspend fun validateResearchLineOwnership(researchLineId: Long, userId: Long) {
        val exists = researchLineRepository.existsByIdAndUserId(researchLineId, userId)
        if (!exists) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Research line not found")
        }
    }
}
