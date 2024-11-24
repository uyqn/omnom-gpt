package no.uyqn.server.services

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import no.uyqn.server.SpringBootIntegrationTest
import no.uyqn.server.TestContainer
import no.uyqn.server.dtos.UserRegistrationDTO
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Container
import reactor.test.StepVerifier

@SpringBootIntegrationTest
class UserPrincipalServiceTest {
    companion object {
        @Container
        private val postgreSQLContainer = TestContainer.postgreSQLContainer

        @JvmStatic
        @DynamicPropertySource
        fun configureProperties(registry: DynamicPropertyRegistry) = TestContainer.configurePostgresProperties(registry)
    }

    @Autowired
    private lateinit var userPrincipalService: UserPrincipalService

    @Autowired
    private lateinit var userService: UserService

    @Test
    fun `should not return user details when user is not found`() {
        StepVerifier
            .create(userPrincipalService.findByUsername("unknown"))
            .expectError(UsernameNotFoundException::class.java)
            .verify()
    }

    @Test
    fun `should return a user details when user is found`() {
        val userRegistrationDTO = UserRegistrationDTO(email = "athena@poop.no", username = "athenapoop", password = "lollobjørnis")
        val result =
            userService
                .register(userRegistrationDTO)
                .flatMap { user -> userPrincipalService.findByUsername(user.username!!).map { userPrincipal -> Pair(user, userPrincipal) } }

        StepVerifier
            .create(result)
            .assertNext { (user, userPrincipal) ->
                user shouldNotBe null
                userPrincipal shouldNotBe null

                userPrincipal.username shouldBe user.username
                userPrincipal.password shouldBe user.password

                userPrincipal.authorities.map { it.authority } shouldBe user.roles.map { it.authority }
            }.verifyComplete()
    }
}
