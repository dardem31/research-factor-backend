package by.dardem.researchfactorbackend.domain.dto.mappers

import by.dardem.researchfactorbackend.domain.dto.UserDto
import by.dardem.researchfactorbackend.domain.entity.User
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface UserMapper {
    fun toDto(user: User): UserDto
    fun toEntity(dto: UserDto): User
}