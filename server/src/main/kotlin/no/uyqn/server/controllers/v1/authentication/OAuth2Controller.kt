package no.uyqn.server.controllers.v1.authentication

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.Pattern
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.net.URI

@RestController
@RequestMapping("/oauth2")
@Tag(name = "OAuth2", description = "OAuth2 authentication")
class OAuth2Controller {
    @Operation(
        summary = "Login with OAuth2 provider",
        description = "Redirect",
    )
    @GetMapping("/login/{provider}")
    fun login(
        @Pattern(regexp = "google|github", message = "Invalid provider")
        @Parameter(description = "The OAuth2 provider to login with", example = "google or github")
        @PathVariable provider: String,
        @Parameter(hidden = true) exchange: ServerWebExchange,
    ): Mono<Void> {
        val redirectUrl = "/login/oauth2/code/$provider"
        val response = exchange.response
        response.statusCode = HttpStatus.FOUND
        response.headers.location = URI.create(redirectUrl)
        return Mono.empty()
    }
}
