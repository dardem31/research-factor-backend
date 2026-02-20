package by.dardem.researchfactorbackend.service
import by.dardem.researchfactorbackend.domain.dto.JwtPayload
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class JwtService(

    @Value("\${jwt.secret}")
    secret: String,

    @Value("\${jwt.issuer}")
    val issuer: String,

    @Value("\${jwt.ttl-seconds}")
    val ttlSeconds: Long
) {

    private val algorithm = Algorithm.HMAC256(secret)

    private val verifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    fun generateToken(userId: Long): String {
        val now = Instant.now()

        return JWT.create()
            .withIssuer(issuer)
            .withSubject(userId.toString())
            .withIssuedAt(Date.from(now))
            .withExpiresAt(Date.from(now.plusSeconds(ttlSeconds)))
            .sign(algorithm)
    }

    fun verify(token: String): DecodedJWT =
        verifier.verify(token)

    fun extractUserId(token: String): Long =
        verify(token).subject.toLong()
}
