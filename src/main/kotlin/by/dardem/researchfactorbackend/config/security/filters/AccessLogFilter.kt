package by.dardem.researchfactorbackend.config.security.filters

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Component
class AccessLogFilter : WebFilter {

    private val accessLog = LoggerFactory.getLogger("accessLog")
    private val nginxDateFormat = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z")

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val request = exchange.request
        val response = exchange.response

        val startTime = System.currentTimeMillis()

        return chain.filter(exchange)
            .doFinally {
                val status = response.statusCode?.value() ?: 0
                if (status in 400..499) {
                    val logEntry = buildNginxLogEntry(exchange, startTime)
                    accessLog.warn(logEntry)
                }
            }
    }

    private fun buildNginxLogEntry(exchange: ServerWebExchange, startTime: Long): String {
        val request = exchange.request
        val response = exchange.response

        return "%s [%s] \"%s\" \"%s\" %d \"%s\" \"%s\" \"%s\"".format(
            getClientIp(exchange),
            getCurrentTimeFormatted(),
            getHost(exchange),
            getRequestLine(exchange),
            response.statusCode?.value() ?: 0,
            getHeaderOrDash(request.headers["Referer"]),
            getHeaderOrDash(request.headers["User-Agent"]),
            getHeaderOrDash(request.headers["X-Forwarded-For"])
        )
    }

    private fun getClientIp(exchange: ServerWebExchange): String {
        val headers = exchange.request.headers
        val xff = headers["X-Forwarded-For"]?.firstOrNull()
        return xff?.split(",")?.firstOrNull()?.trim()
            ?: exchange.request.remoteAddress?.address?.hostAddress
            ?: "-"
    }

    private fun getCurrentTimeFormatted(): String =
        ZonedDateTime.now(ZoneOffset.UTC).format(nginxDateFormat)

    private fun getHost(exchange: ServerWebExchange): String =
        exchange.request.headers["Host"]?.firstOrNull() ?: "-"

    private fun getRequestLine(exchange: ServerWebExchange): String {
        val request = exchange.request
        val query = request.uri.rawQuery
        return "${request.method} ${request.uri.rawPath}${if (query != null) "?$query" else ""}"
    }

    private fun getHeaderOrDash(headerValues: List<String>?): String =
        headerValues?.firstOrNull() ?: "-"
}
