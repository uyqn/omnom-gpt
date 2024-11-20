package no.uyqn.server.controllers.v1.users

import jakarta.validation.Valid
import no.uyqn.server.controllers.v1.users.dtos.UserDTO
import no.uyqn.server.controllers.v1.users.dtos.UserRegistrationDTO
import no.uyqn.server.services.users.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/users")
class UsersController(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
) {
    @PostMapping("/register")
    fun register(
        @Valid @RequestBody dto: UserRegistrationDTO,
    ): Mono<UserDTO> = userService.register(dto.copy(password = passwordEncoder.encode(dto.password)))
}
