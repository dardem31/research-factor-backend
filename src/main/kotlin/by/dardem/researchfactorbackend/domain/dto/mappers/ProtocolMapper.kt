package by.dardem.researchfactorbackend.domain.dto.mappers

import by.dardem.researchfactorbackend.domain.dto.research.ProtocolDto
import by.dardem.researchfactorbackend.domain.entity.research.StudyProtocol
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface ProtocolMapper {

    fun toDto(entity: StudyProtocol): ProtocolDto

    @Mapping(target = "research", ignore = true)
    fun toEntity(dto: ProtocolDto): StudyProtocol
}
