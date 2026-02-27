package by.dardem.researchfactorbackend.service.domain.research_line

import by.dardem.researchfactorbackend.domain.dto.mappers.StageQuestionMapper
import by.dardem.researchfactorbackend.domain.dto.research_line.StageQuestionDto
import by.dardem.researchfactorbackend.domain.entity.research_line.StageQuestion
import by.dardem.researchfactorbackend.repository.entity.StageQuestionRepository
import by.dardem.researchfactorbackend.service.domain.base.BaseService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class StageQuestionService(
    private val stageQuestionRepository: StageQuestionRepository,
    private val researchLineService: ResearchLineService,
    private val stageQuestionMapper: StageQuestionMapper
) : BaseService<StageQuestion, Long>(stageQuestionRepository) {

    suspend fun createStageQuestion(dto: StageQuestionDto, userId: Long): StageQuestionDto {
        researchLineService.validateResearchLineOwnership(dto.researchLineId, userId)

        val stageQuestion = stageQuestionMapper.toEntity(dto)
        val savedQuestion = save(stageQuestion)
        return stageQuestionMapper.toDto(savedQuestion)
    }

    suspend fun updateStageQuestion(dto: StageQuestionDto, userId: Long): StageQuestionDto {
        val id = dto.id ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Stage question id is required")

        val stageQuestion = stageQuestionRepository.findById(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Stage question not found")

        researchLineService.validateResearchLineOwnership(stageQuestion.researchLineId, userId)

        stageQuestionMapper.updateEntityFromDto(dto, stageQuestion)
        val savedQuestion = saveOrUpdate(stageQuestion)
        return stageQuestionMapper.toDto(savedQuestion)
    }

    suspend fun deleteStageQuestion(id: Long, userId: Long) {
        val stageQuestion = stageQuestionRepository.findById(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Stage question not found")

        researchLineService.validateResearchLineOwnership(stageQuestion.researchLineId, userId)
        stageQuestionRepository.deleteById(id)
    }

    suspend fun getStageQuestionsByResearchLineId(researchLineId: Long, userId: Long): List<StageQuestionDto> {
        return stageQuestionRepository.findAllByResearchLineId(researchLineId)
            .map(stageQuestionMapper::toDto)
    }
}
