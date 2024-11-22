package no.uyqn.server.validations

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import no.uyqn.server.dtos.UserRegistrationDTO

class EmailOrUsernameValidator : ConstraintValidator<EmailOrUsernameRequired, UserRegistrationDTO> {
    override fun isValid(
        dto: UserRegistrationDTO?,
        constraintValidatorContext: ConstraintValidatorContext?,
    ): Boolean = dto?.email != null || dto?.username != null
}
