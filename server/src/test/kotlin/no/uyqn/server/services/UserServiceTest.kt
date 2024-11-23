package no.uyqn.server.services

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import no.uyqn.server.TestContainer
import no.uyqn.server.configurations.initializers.DotenvInitializer
import no.uyqn.server.configurations.initializers.OpenAiConfigurationInitializer
import no.uyqn.server.dtos.UserRegistrationDTO
import no.uyqn.server.exceptions.UserRegistrationException
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.lang.NullPointerException

@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = [DotenvInitializer::class, OpenAiConfigurationInitializer::class])
class UserServiceTest {
    companion object {
        @Container
        private val postgresContainer = TestContainer.postgreSQLContainer

        @JvmStatic
        @DynamicPropertySource
        fun configureProperties(registry: DynamicPropertyRegistry) = TestContainer.configurePostgresProperties(registry)
    }

    @Autowired
    private lateinit var userService: UserService

    @Test
    fun `should register new users`() {
        val userRegistrationDTO = UserRegistrationDTO(email = "athena@poop.no", username = "athenapoop", password = "bæbælillelam!")
        val user = userService.register(userRegistrationDTO).block()

        user shouldNotBe null

        user!!.id shouldBeGreaterThan 0
        user.email shouldBe userRegistrationDTO.email
        user.username shouldNotBe userRegistrationDTO.password
    }

    @Test
    fun `should throw user registration exception when user already exists`() {
        val userRegistrationDTO = UserRegistrationDTO(email = "athena@poop.no", username = "athenapoop", password = "bæbælillelam!")
        shouldThrow<UserRegistrationException> { userService.register(userRegistrationDTO).block() }
    }

    @Test
    fun `should throw null pointer exception when both email and username are null`() {
        val userRegistrationDTO = UserRegistrationDTO(email = null, username = null, password = "bæbælillelam!")
        shouldThrow<NullPointerException> { userService.register(userRegistrationDTO).block() }
    }
}
