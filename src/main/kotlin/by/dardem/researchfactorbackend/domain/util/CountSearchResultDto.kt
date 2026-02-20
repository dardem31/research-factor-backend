package by.dardem.researchfactorbackend.domain.util
import org.springframework.http.ResponseEntity

class CountSearchResultDto(val count: Long) {
    val limitExceeded = false

    companion object {
        fun okResponse(count: Long): ResponseEntity<CountSearchResultDto> {
            return ResponseEntity.ok(CountSearchResultDto(count))
        }
    }
}
