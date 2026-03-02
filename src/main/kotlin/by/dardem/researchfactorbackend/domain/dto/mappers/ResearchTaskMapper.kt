package by.dardem.researchfactorbackend.domain.dto.mappers

import by.dardem.researchfactorbackend.domain.dto.research_line.task.ResearchTaskDto
import by.dardem.researchfactorbackend.domain.entity.research_line.ResearchTask
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface ResearchTaskMapper {

    fun toDto(entity: ResearchTask): ResearchTaskDto

    @Mapping(target = "userId", ignore = true)
    fun toEntity(dto: ResearchTaskDto): ResearchTask

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "researchLineId", ignore = true)
    fun updateEntityFromDto(dto: ResearchTaskDto, @MappingTarget entity: ResearchTask): ResearchTask
}
