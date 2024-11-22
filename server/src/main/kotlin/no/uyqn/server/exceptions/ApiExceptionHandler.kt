package no.uyqn.server.exceptions

import no.uyqn.server.exceptions.users.UserRegistrationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@ControllerAdvice
class ApiExceptionHandler {
    @ExceptionHandler(UserRegistrationException::class)
    fun handleUserRegistrationException(
        exception: UserRegistrationException,
        exchange: ServerWebExchange,
    ): Mono<ResponseEntity<ApiError>> = createApiError(HttpStatus.CONFLICT, exception, exchange)

    @ExceptionHandler(WebExchangeBindException::class)
    fun handleMethodArgumentTypeMismatchException(
        exception: WebExchangeBindException,
        exchange: ServerWebExchange,
    ): Mono<ResponseEntity<ApiError>> {
        val message =
            exception.bindingResult.allErrors
                .mapIndexed { index, error -> "${index + 1}: ${error.defaultMessage}" }
                .joinToString("\n")
        return createApiError(HttpStatus.BAD_REQUEST, message, exchange)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(
        exception: Exception,
        exchange: ServerWebExchange,
    ): Mono<ResponseEntity<ApiError>> = createApiError(HttpStatus.INTERNAL_SERVER_ERROR, exception, exchange)

    private fun <E : Exception> createApiError(
        httpStatus: HttpStatus,
        exception: E,
        exchange: ServerWebExchange,
    ): Mono<ResponseEntity<ApiError>> = createApiError(httpStatus, exception.message, exchange)

    private fun createApiError(
        httpStatus: HttpStatus,
        message: String? = httpStatus.reasonPhrase,
        exchange: ServerWebExchange,
    ): Mono<ResponseEntity<ApiError>> =
        Mono.just(
            ResponseEntity.status(httpStatus).body(
                ApiError(
                    status = httpStatus,
                    message = message,
                    path = "${exchange.request.method} ${exchange.request.path.value()}",
                ),
            ),
        )
}
