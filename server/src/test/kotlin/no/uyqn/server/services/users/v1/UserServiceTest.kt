package no.uyqn.server.services.users.v1

import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldNotBe
import no.uyqn.server.AbstractIntegrationTest
import no.uyqn.server.dtos.UserRegistrationDTO
import no.uyqn.server.services.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertNotNull

class UserServiceTest : AbstractIntegrationTest() {
    @Autowired
    private lateinit var userService: UserService

    @Test
    fun `should register a user`() {
        val userRegistration =
            UserRegistrationDTO(
                email = "athena@poop.no",
                username = "athenapoop",
                password = "bæbælillelam",
            )
        val user = userService.register(userRegistration).block()
        user shouldNotBe null
        assertNotNull(user)

        user.id shouldBeGreaterThan 0
    }
}
