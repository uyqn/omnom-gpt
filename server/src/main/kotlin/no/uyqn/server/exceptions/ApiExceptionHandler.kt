package no.uyqn.server.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
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
    ): Mono<ResponseEntity<ApiError>> = createApiErrorResponse(HttpStatus.CONFLICT, exception, exchange)

    @ExceptionHandler(WebExchangeBindException::class)
    fun handleMethodArgumentTypeMismatchException(
        exception: WebExchangeBindException,
        exchange: ServerWebExchange,
    ): Mono<ResponseEntity<ApiError>> {
        val message =
            exception.bindingResult.allErrors
                .mapIndexed { index, error -> "${index + 1}: ${error.defaultMessage}" }
                .joinToString("\n")
        return createApiErrorResponse(HttpStatus.BAD_REQUEST, message, exchange)
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialsException(
        exception: BadCredentialsException,
        exchange: ServerWebExchange,
    ): Mono<ResponseEntity<ApiError>> = createApiErrorResponse(HttpStatus.UNAUTHORIZED, exception, exchange)

    @ExceptionHandler(Exception::class)
    fun handleException(
        exception: Exception,
        exchange: ServerWebExchange,
    ): Mono<ResponseEntity<ApiError>> = createApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception, exchange)

    private fun <E : Exception> createApiErrorResponse(
        httpStatus: HttpStatus,
        exception: E,
        exchange: ServerWebExchange,
    ): Mono<ResponseEntity<ApiError>> = createApiErrorResponse(httpStatus, exception.message, exchange)

    private fun createApiErrorResponse(
        httpStatus: HttpStatus,
        message: String? = httpStatus.reasonPhrase,
        exchange: ServerWebExchange,
    ): Mono<ResponseEntity<ApiError>> =
        Mono.just(
            ResponseEntity.status(httpStatus).body(
                createApiError(httpStatus, message, exchange),
            ),
        )

    private fun createApiError(
        httpStatus: HttpStatus,
        message: String? = httpStatus.reasonPhrase,
        exchange: ServerWebExchange,
    ) = ApiError(
        status = httpStatus,
        message = message,
        path = "${exchange.request.method} ${exchange.request.path.value()}",
    )
}
