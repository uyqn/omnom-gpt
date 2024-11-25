package no.uyqn.server.controllers.v1

import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import no.uyqn.server.SpringBootIntegrationTest
import no.uyqn.server.TestContainer
import no.uyqn.server.dtos.UserDTO
import no.uyqn.server.dtos.UserRegistrationDTO
import no.uyqn.server.exceptions.ApiError
import no.uyqn.server.exceptions.ApiExceptionHandler
import no.uyqn.server.services.UserService
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.testcontainers.junit.jupiter.Container
import kotlin.test.Test

@SpringBootIntegrationTest
class UserControllerTest {
    companion object {
        @Container
        private val postgreSQLContainer = TestContainer.postgreSQLContainer

        @JvmStatic
        @DynamicPropertySource
        fun configureProperties(registry: DynamicPropertyRegistry) = TestContainer.configurePostgresProperties(registry)
    }

    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var userController: UserController

    @Autowired
    private lateinit var apiExceptionHandler: ApiExceptionHandler

    @BeforeEach
    fun setUp() {
        webTestClient =
            WebTestClient
                .bindToController(userController)
                .controllerAdvice(apiExceptionHandler)
                .build()
    }

    @Test
    fun `should register new users`() {
        val request = UserRegistrationDTO(email = "athena@poop.no", username = "athenapoop", password = "kakemin12mø")

        webTestClient
            .post()
            .uri("/users/register")
            .bodyValue(request)
            .exchange()
            .expectStatus()
            .isOk
            .expectBody<UserDTO>()
            .consumeWith {
                val responseBody = it.responseBody!!
                responseBody.id shouldBeGreaterThan 0
                responseBody.email shouldBe request.email
                responseBody.username shouldBe request.username
                responseBody.password shouldNotBe request.password
            }
    }

    @Test
    fun `should throw ApiError when user already exists`() {
        val request = UserRegistrationDTO(email = "athena@poop2.no", username = "athenapoop2", password = "kakemin12mø")
        userService.register(request).block()

        webTestClient
            .post()
            .uri("/users/register")
            .bodyValue(request)
            .exchange()
            .expectStatus()
            .is4xxClientError
            .expectBody<ApiError>()
            .consumeWith {
                val responseBody = it.responseBody!!
                responseBody.status shouldBe HttpStatus.CONFLICT
                responseBody.message shouldBe "Username or email already taken"
            }
    }

    @Test
    fun `should throw bad request when email and password is null`() {
        val request = UserRegistrationDTO(email = null, username = null, password = "kamehameha")
        webTestClient
            .post()
            .uri("/users/register")
            .bodyValue(request)
            .exchange()
            .expectStatus()
            .isBadRequest
            .expectBody<ApiError>()
            .consumeWith {
                val responseBody = it.responseBody!!
                responseBody.status shouldBe HttpStatus.BAD_REQUEST
                responseBody.message shouldBe "1: Either email or username is required"
            }
    }

    @Test
    fun `should throw bad request when email and password is blank`() {
        val request = UserRegistrationDTO(email = "", username = "", password = "kamehameha")
        webTestClient
            .post()
            .uri("/users/register")
            .bodyValue(request)
            .exchange()
            .expectStatus()
            .isBadRequest
            .expectBody<ApiError>()
            .consumeWith {
                val responseBody = it.responseBody!!
                responseBody.status shouldBe HttpStatus.BAD_REQUEST
                responseBody.message shouldBe "1: Either email or username is required"
            }
    }
}
