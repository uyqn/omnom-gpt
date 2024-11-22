package no.uyqn.server.controllers.v1.users.dtos

import io.kotest.matchers.shouldBe
import no.uyqn.server.dtos.UserRegistrationDTO
import org.junit.jupiter.api.Test

class UserRegistrationDTOTest {
    @Test
    fun `test property accessor`() {
        val userRegistrationDTO =
            UserRegistrationDTO(
                email = "athena@poop.no",
                username = "athenapoop",
                password = "bæbælillelam",
            )

        userRegistrationDTO.email shouldBe "athena@poop.no"
        userRegistrationDTO.username shouldBe "athenapoop"
        userRegistrationDTO.password shouldBe "bæbælillelam"
    }
}
