package no.uyqn.server.controllers.v1.authentication

import jakarta.validation.Valid
import no.uyqn.server.controllers.v1.authentication.dtos.LoginRequestDTO
import no.uyqn.server.controllers.v1.authentication.dtos.TokenDTO
import no.uyqn.server.services.jwt.JwtTokenService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController(
    private val jwtTokenService: JwtTokenService,
) {
    @PostMapping("/login")
    fun login(
        @Valid @RequestBody loginRequestDTO: LoginRequestDTO,
    ): Mono<TokenDTO> =
        jwtTokenService
            .authenticate(loginRequestDTO)
            .flatMap { jwtTokenService.generateToken(it) }

    @PostMapping("/validate")
    fun validate(): Mono<String> = Mono.just("Validated")
}
