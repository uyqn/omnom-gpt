package no.uyqn.server.controllers.v1

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import no.uyqn.server.dtos.UserDTO
import no.uyqn.server.dtos.UserRegistrationDTO
import no.uyqn.server.services.users.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "User operations")
class UsersController(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
) {
    @Operation(
        summary = "Register a new user",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "User registered successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = UserDTO::class),
                    ),
                ],
            ),
        ],
    )
    @PostMapping("/register", consumes = ["application/json"], produces = ["application/json"])
    fun register(
        @Valid @RequestBody dto: UserRegistrationDTO,
    ): Mono<UserDTO> = userService.register(dto.copy(password = passwordEncoder.encode(dto.password)))
}
