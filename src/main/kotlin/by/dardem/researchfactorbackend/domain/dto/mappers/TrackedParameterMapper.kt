package by.dardem.researchfactorbackend.domain.dto.mappers

import by.dardem.researchfactorbackend.domain.dto.research.TrackedParameterDto
import by.dardem.researchfactorbackend.domain.entity.research.TrackedParameter
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface TrackedParameterMapper {

    fun toDto(entity: TrackedParameter): TrackedParameterDto

    @Mapping(target = "research", ignore = true)
    fun toEntity(dto: TrackedParameterDto): TrackedParameter
}
