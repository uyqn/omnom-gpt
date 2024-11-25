package no.uyqn.server.services

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import no.uyqn.server.SpringBootIntegrationTest
import no.uyqn.server.TestContainer
import no.uyqn.server.dtos.UserRegistrationDTO
import no.uyqn.server.exceptions.UserRegistrationException
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Container
import reactor.kotlin.test.test

@SpringBootIntegrationTest
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
        userService
            .register(userRegistrationDTO)
            .test()
            .assertNext {
                it shouldNotBe null
                it.id shouldBeGreaterThan 0
                it.email shouldBe userRegistrationDTO.email
                it.username shouldBe userRegistrationDTO.username
            }.verifyComplete()
    }

    @Test
    fun `should register new users with just email`() {
        val userRegistrationDTO = UserRegistrationDTO(email = "athena2@poop.no", username = null, password = "bæbælillelam!")
        userService
            .register(userRegistrationDTO)
            .test()
            .assertNext {
                it shouldNotBe null
                it.id shouldBeGreaterThan 0
                it.email shouldBe userRegistrationDTO.email
                it.username shouldBe null
            }.verifyComplete()
    }

    @Test
    fun `should register new users with just username`() {
        val userRegistrationDTO = UserRegistrationDTO(email = null, username = "justathena", password = "bæbælillelam!")
        userService
            .register(userRegistrationDTO)
            .test()
            .assertNext {
                it shouldNotBe null
                it.id shouldBeGreaterThan 0
                it.email shouldBe null
                it.username shouldBe userRegistrationDTO.username
            }.verifyComplete()
    }

    @Test
    fun `should throw user registration exception when user already exists`() {
        val userRegistrationDTO = UserRegistrationDTO(email = "athena@poop.no", username = "athenapoop", password = "bæbælillelam!")
        userService
            .register(userRegistrationDTO)
            .test()
            .expectError(UserRegistrationException::class.java)
            .verify()
    }

    @Test
    fun `should throw null pointer exception when both email and username are null`() {
        val userRegistrationDTO = UserRegistrationDTO(email = null, username = null, password = "bæbælillelam!")
        shouldThrow<NullPointerException> { userService.register(userRegistrationDTO).block() }
    }
}
