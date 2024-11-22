package no.uyqn.server.exceptions

import org.springframework.http.HttpStatus
import java.time.Instant

data class ApiError(
    val status: HttpStatus,
    val code: Int = status.value(),
    val message: String? = status.reasonPhrase,
    val timestamp: Instant = Instant.now(),
    val path: String,
)
