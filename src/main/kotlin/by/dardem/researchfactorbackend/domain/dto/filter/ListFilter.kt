package by.dardem.researchfactorbackend.domain.dto.filter

import org.springframework.data.domain.Pageable

abstract class ListFilter {
    val pagination: PageableDto = PageableDto()

    fun toPageable(): Pageable {
        return pagination.toPageable()
    }
}
