package by.dardem.researchfactorbackend.domain.beans.exceptions
class TechnicalException : RuntimeException {
    constructor(message: String, statusCode: Int, responseBody: String?) : super(
        """
        Failed to process the request:
        Message: $message
        Response code: $statusCode
        Response body: ${responseBody ?: "N/A"}
        """.trimIndent()
    )

    constructor(message: String, cause: Throwable) : super(message, cause)
}