package no.uyqn.server.controllers.v1

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import no.uyqn.server.configurations.initializers.DotenvInitializer
import no.uyqn.server.configurations.initializers.OpenAiConfigurationInitializer
import no.uyqn.server.dtos.RoleDTO
import no.uyqn.server.dtos.UserDTO
import no.uyqn.server.dtos.UserRegistrationDTO
import no.uyqn.server.exceptions.ApiError
import no.uyqn.server.exceptions.ApiExceptionHandler
import no.uyqn.server.exceptions.UserRegistrationException
import no.uyqn.server.models.Role
import no.uyqn.server.services.UserService
import org.junit.jupiter.api.BeforeEach
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import kotlin.test.Test

@SpringBootTest
@ContextConfiguration(initializers = [DotenvInitializer::class, OpenAiConfigurationInitializer::class])
class UserControllerTest {
    private lateinit var webTestClient: WebTestClient

    @MockBean
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
        val response =
            Mono.just(
                UserDTO(
                    id = 1,
                    email = request.email,
                    username = request.username,
                    password = "encrypted ${request.password}",
                    roles = mutableListOf(RoleDTO(authority = Role.USER.authority)),
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now(),
                ),
            )

        given(userService.register(request)).willReturn(response)

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
                responseBody.id shouldBe 1
                responseBody.email shouldBe request.email
                responseBody.username shouldBe request.username
                responseBody.password shouldNotBe request.password
            }
    }

    @Test
    fun `should throw ApiError when user already exists`() {
        val request = UserRegistrationDTO(email = "athena@poop.no", username = "athenapoop", password = "kakemin12mø")
        given(userService.register(request)).willThrow(UserRegistrationException("User already exists"))

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
                responseBody.message shouldBe "User already exists"
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
