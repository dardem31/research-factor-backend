package by.dardem.researchfactorbackend
import java.time.ZoneId

object IConstants {
    const val DEFAULT_LOCALE = "en"
    const val DEFAULT_TIMEZONE_ID = "UTC"
    val DEFAULT_TIMEZONE: ZoneId = ZoneId.of(DEFAULT_TIMEZONE_ID)
    const val DEFAULT_PAGE_SIZE = 10
}
