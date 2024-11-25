package no.uyqn.server.validations

import io.kotest.matchers.shouldBe
import no.uyqn.server.dtos.UserRegistrationDTO
import org.junit.jupiter.api.Test

class EmailOrUsernameValidatorTest {
    @Test
    fun `should return true when email is present`() {
        val dto = UserRegistrationDTO(email = "some@email.com", username = null, password = "password123")
        val validator = EmailOrUsernameValidator()
        validator.isValid(dto, null) shouldBe true
    }

    @Test
    fun `should return true when username is present`() {
        val dto = UserRegistrationDTO(email = null, username = "someusername", password = "password123")
        val validator = EmailOrUsernameValidator()
        validator.isValid(dto, null) shouldBe true
    }

    @Test
    fun `should return false when email and username are missing`() {
        val dto = UserRegistrationDTO(email = null, username = null, password = "password123")
        val validator = EmailOrUsernameValidator()
        validator.isValid(dto, null) shouldBe false
    }

    @Test
    fun `should return false when email and username are empty`() {
        val dto = UserRegistrationDTO(email = "", username = "", password = "password123")
        val validator = EmailOrUsernameValidator()
        validator.isValid(dto, null) shouldBe false
    }
}
