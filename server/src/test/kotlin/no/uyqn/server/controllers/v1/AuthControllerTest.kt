package no.uyqn.server.controllers.v1

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import no.uyqn.server.SpringBootIntegrationTest
import no.uyqn.server.TestContainer
import no.uyqn.server.dtos.LoginRequestDTO
import no.uyqn.server.dtos.TokenDTO
import no.uyqn.server.dtos.UserRegistrationDTO
import no.uyqn.server.exceptions.ApiExceptionHandler
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.testcontainers.junit.jupiter.Container

@SpringBootIntegrationTest
class AuthControllerTest {
    companion object {
        @Container
        private val postgreSQLContainer = TestContainer.postgreSQLContainer

        @JvmStatic
        @DynamicPropertySource
        fun configureProperties(registry: DynamicPropertyRegistry) = TestContainer.configurePostgresProperties(registry)
    }

    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var authController: AuthController

    @Autowired
    private lateinit var userController: UserController

    @Autowired
    private lateinit var apiExceptionHandler: ApiExceptionHandler

    @BeforeEach
    fun setUp() {
        webTestClient =
            WebTestClient
                .bindToController(authController, userController)
                .controllerAdvice(apiExceptionHandler)
                .build()
    }

    @Test
    fun `should return token when login is successful`() {
        val user =
            UserRegistrationDTO(
                email = "athena@poop.no",
                username = "athenapoop",
                password = "password123",
            )

        userController.register(user).block()

        val login =
            LoginRequestDTO(
                username = user.username!!,
                password = user.password,
            )

        webTestClient
            .post()
            .uri("/auth/login")
            .bodyValue(login)
            .exchange()
            .expectStatus()
            .isOk
            .expectBody<TokenDTO>()
            .consumeWith {
                val responseBody = it.responseBody!!
                responseBody.token shouldNotBe null
                responseBody.sub shouldBe user.username
            }
    }

    @Test
    fun `should return 401 with incorrect credentials`() {
        val login =
            LoginRequestDTO(
                username = "athenapoop",
                password = "thisIsIncorrectPassword",
            )

        webTestClient
            .post()
            .uri("/auth/login")
            .bodyValue(login)
            .exchange()
            .expectStatus()
            .isUnauthorized
    }
}
