package no.uyqn.server.controllers.v1

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import no.uyqn.server.dtos.TokenDTO
import no.uyqn.server.services.JwtTokenService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication operations")
class AuthController(
    private val jwtTokenService: JwtTokenService,
) {
    @Operation(
        summary = "Login",
        description = "Authenticate and generate a JWT token",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "JWT token generated successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema =
                            Schema(implementation = TokenDTO::class),
                    ),
                ],
            ),
        ],
    )
    @PostMapping("/login", consumes = ["application/json"], produces = ["application/json"])
    fun login(
        @Valid @RequestBody loginRequestDTO: LoginRequestDTO,
    ): Mono<TokenDTO> =
        jwtTokenService
            .authenticate(loginRequestDTO)
            .flatMap { jwtTokenService.generateToken(it) }
}
