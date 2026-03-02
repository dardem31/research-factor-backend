package by.dardem.researchfactorbackend.domain.dto.mappers

import by.dardem.researchfactorbackend.domain.dto.research_line.artifact.ArtifactDto
import by.dardem.researchfactorbackend.domain.entity.research_line.Artifact
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface ArtifactMapper {

    fun toDto(entity: Artifact): ArtifactDto

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "storageUrl", ignore = true)
    @Mapping(target = "sha256", ignore = true)
    @Mapping(target = "metadata", ignore = true)
    fun toEntity(dto: ArtifactDto): Artifact

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "taskId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "storageUrl", ignore = true)
    @Mapping(target = "sha256", ignore = true)
    @Mapping(target = "metadata", ignore = true)
    fun updateEntityFromDto(dto: ArtifactDto, @MappingTarget entity: Artifact): Artifact
}
