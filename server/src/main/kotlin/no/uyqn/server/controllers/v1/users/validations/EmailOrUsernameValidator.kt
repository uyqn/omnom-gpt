package no.uyqn.server.controllers.v1.users.validations

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import no.uyqn.server.controllers.v1.users.dtos.UserRegistrationDTO

class EmailOrUsernameValidator : ConstraintValidator<EmailOrUsernameRequired, UserRegistrationDTO> {
    override fun isValid(
        dto: UserRegistrationDTO?,
        constraintValidatorContext: ConstraintValidatorContext?,
    ): Boolean = dto?.email != null || dto?.username != null
}
