package no.uyqn.server.services

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import no.uyqn.server.AbstractIntegrationTest
import no.uyqn.server.dtos.UserRegistrationDTO
import no.uyqn.server.exceptions.UserRegistrationException
import no.uyqn.server.repositories.UsersRepository
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import java.lang.NullPointerException

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class UserServiceTest : AbstractIntegrationTest() {
    private val logger: Logger = LoggerFactory.getLogger(UserServiceTest::class.java)

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var usersRepository: UsersRepository

    @Test
    @Order(1)
    fun `should return user when user has not been registered`() {
        val userRegistrationDTO = UserRegistrationDTO(email = "athena@poop.no", username = "athenapoop", password = "bæbælillelam!")
        val user = userService.register(userRegistrationDTO).block()

        user shouldNotBe null
        logger.info("created user: $user")

        user!!.id shouldBeGreaterThan 0
        user.email shouldBe userRegistrationDTO.email
        user.username shouldNotBe userRegistrationDTO.password

        val savedUser = usersRepository.findById(user.id).block()
        savedUser shouldNotBe null
    }

    @Test
    @Order(2)
    fun `should throw UserRegistrationException when user already exists`() {
        logger.info(
            "users: " +
                usersRepository
                    .findAll()
                    .collectList()
                    .block()
                    .toString(),
        )
        val userRegistrationDTO = UserRegistrationDTO(email = "athena@poop.no", username = "athenapoop", password = "bæbælillelam!")
        shouldThrow<UserRegistrationException> { userService.register(userRegistrationDTO).block() }
    }

    @Test
    fun `should throw null pointer exception when user tries to save with without username and email`() {
        val userRegistrationDTO = UserRegistrationDTO(email = null, username = null, password = "bæbælillelam!")
        shouldThrow<NullPointerException> { userService.register(userRegistrationDTO).block() }
    }
}
