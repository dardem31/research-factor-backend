package by.dardem.researchfactorbackend.domain.dto.filter

import by.dardem.researchfactorbackend.IConstants.DEFAULT_PAGE_SIZE
import by.dardem.researchfactorbackend.domain.enums.PaginationOrderEnum
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Order
import jakarta.persistence.criteria.Predicate
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class PageableDto {
    var limit = 0
    var startFrom: String? = null
    var order: PaginationOrderEnum? = null


    fun toPageable(): Pageable {
        return if (limit == 0) PageRequest.of(0, DEFAULT_PAGE_SIZE) else PageRequest.of(0, limit)
    }

    fun apply(
        predicates: MutableList<Predicate>,
        idProp: Expression<out Comparable<*>>,
        builder: CriteriaBuilder,
        idType: Class<*>
    ): Order? {
        if (order != null && startFrom != null) {
            val parsedValue: Comparable<*> = when (idType) {
                java.lang.Long::class.java, Long::class.java -> startFrom!!.toLong()
                Instant::class.java -> LocalDateTime.parse(startFrom!!).toInstant(ZoneOffset.UTC)
                else -> throw IllegalArgumentException("Unsupported id type: $idType")
            }

            @Suppress("UNCHECKED_CAST")
            if (PaginationOrderEnum.prev == order) {
                predicates.add(
                    builder.greaterThan(idProp as Expression<Comparable<Any>>, parsedValue as Comparable<Any>)
                )
                return builder.asc(idProp)
            } else {
                predicates.add(
                    builder.lessThan(idProp as Expression<Comparable<Any>>, parsedValue as Comparable<Any>)
                )
                return builder.desc(idProp)
            }
        }
        return null
    }
}
