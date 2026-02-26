package by.dardem.researchfactorbackend.domain.dto.mappers

import by.dardem.researchfactorbackend.domain.dto.research.PrimaryOutcomeDto
import by.dardem.researchfactorbackend.domain.entity.research.PrimaryOutcome
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface PrimaryOutcomeMapper {

    fun toDto(entity: PrimaryOutcome): PrimaryOutcomeDto

    @Mapping(target = "research", ignore = true)
    @Mapping(target = "status", ignore = true)
    fun toEntity(dto: PrimaryOutcomeDto): PrimaryOutcome
}
