package by.dardem.researchfactorbackend.domain.dto.mappers

import by.dardem.researchfactorbackend.domain.dto.research_line.ResearchLineDto
import by.dardem.researchfactorbackend.domain.entity.research_line.ResearchLine
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface ResearchLineMapper {

    fun toDto(entity: ResearchLine): ResearchLineDto

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "actualStartDate", ignore = true)
    @Mapping(target = "actualEndDate", ignore = true)
    fun toEntity(dto: ResearchLineDto): ResearchLine

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "researchId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "actualStartDate", ignore = true)
    @Mapping(target = "actualEndDate", ignore = true)
    fun updateEntityFromDto(dto: ResearchLineDto, @MappingTarget entity: ResearchLine): ResearchLine
}
