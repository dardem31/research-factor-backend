package by.dardem.researchfactorbackend.domain.dto.mappers

import by.dardem.researchfactorbackend.domain.dto.research_line.StageQuestionDto
import by.dardem.researchfactorbackend.domain.entity.research_line.StageQuestion
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface StageQuestionMapper {

    fun toDto(entity: StageQuestion): StageQuestionDto

    fun toEntity(dto: StageQuestionDto): StageQuestion

    @Mapping(target = "researchLineId", ignore = true)
    fun updateEntityFromDto(dto: StageQuestionDto, @MappingTarget entity: StageQuestion): StageQuestion
}
