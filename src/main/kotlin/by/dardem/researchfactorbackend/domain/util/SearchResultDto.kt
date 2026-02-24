package by.dardem.researchfactorbackend.domain.util
import org.springframework.data.domain.Page

data class SearchResultDto<R>(
    var items: List<R>,
    var hasMore: Boolean
) {
    companion object {
        fun <R> fromPage(searchResult: Page<R>): SearchResultDto<R> {
            return SearchResultDto(
                items = searchResult.content,
                hasMore = searchResult.hasNext()
            )
        }
        fun <T, R> fromPage(searchResult: Page<T>, convertToDto: (T) -> R): SearchResultDto<R> {
            return SearchResultDto(
                items = searchResult.content.map(convertToDto),
                hasMore = searchResult.hasNext()
            )
        }
    }
}