package no.uyqn.server.services

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import no.uyqn.server.AbstractIntegrationTest
import no.uyqn.server.dtos.UserRegistrationDTO
import no.uyqn.server.exceptions.UserRegistrationException
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import java.lang.NullPointerException

class UserServiceTest : AbstractIntegrationTest() {
    private val logger: Logger = LoggerFactory.getLogger(UserServiceTest::class.java)

    @Autowired
    private lateinit var userService: UserService

    @Test
    fun `should register new users`() {
        val userRegistrationDTO = UserRegistrationDTO(email = "athena@poop.no", username = "athenapoop", password = "bæbælillelam!")
        val user = userService.register(userRegistrationDTO).block()

        user shouldNotBe null
        logger.info("created user: $user")

        user!!.id shouldBeGreaterThan 0
        user.email shouldBe userRegistrationDTO.email
        user.username shouldNotBe userRegistrationDTO.password

        shouldThrow<UserRegistrationException> { userService.register(userRegistrationDTO).block() }
    }

    @Test
    fun `should throw null pointer exception when both email and username are null`() {
        val userRegistrationDTO = UserRegistrationDTO(email = null, username = null, password = "bæbælillelam!")
        shouldThrow<NullPointerException> { userService.register(userRegistrationDTO).block() }
    }
}
