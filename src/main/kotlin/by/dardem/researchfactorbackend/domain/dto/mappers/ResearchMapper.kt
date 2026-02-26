package by.dardem.researchfactorbackend.domain.dto.mappers

import by.dardem.researchfactorbackend.domain.dto.research.ResearchDto
import by.dardem.researchfactorbackend.domain.entity.research.Research
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.ReportingPolicy

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = [ProtocolMapper::class, PrimaryOutcomeMapper::class, TrackedParameterMapper::class]
)
interface ResearchMapper {

    fun toDto(entity: Research): ResearchDto

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "status", ignore = true)
    fun toEntity(dto: ResearchDto): Research

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "primaryOutcomes", ignore = true)
    @Mapping(target = "trackedParameters", ignore = true)
    fun updateEntityFromDto(dto: ResearchDto, @MappingTarget entity: Research): Research
}
