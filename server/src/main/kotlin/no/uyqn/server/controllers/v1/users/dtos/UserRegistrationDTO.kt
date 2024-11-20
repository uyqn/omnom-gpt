package no.uyqn.server.controllers.v1.users.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import no.uyqn.server.controllers.v1.users.validations.EmailOrUsernameRequired

@EmailOrUsernameRequired
data class UserRegistrationDTO(
    val email: String?,
    val username: String?,
    @field:NotBlank(message = "Password is required")
    @field:Size(min = 8, message = "Password must be at least 8 characters long")
    val password: String,
)
