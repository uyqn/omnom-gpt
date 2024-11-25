package no.uyqn.server.services

import no.uyqn.server.SpringBootIntegrationTest
import no.uyqn.server.TestContainer
import no.uyqn.server.TestContainer.postgreSQLContainer
import no.uyqn.server.dtos.LoginRequestDTO
import no.uyqn.server.dtos.UserRegistrationDTO
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Container
import reactor.kotlin.test.test

@SpringBootIntegrationTest
class JwtTokenServiceTest {
    companion object {
        @Container
        private val postgreSQLContainer = TestContainer.postgreSQLContainer

        @JvmStatic
        @DynamicPropertySource
        fun configureProperties(registry: DynamicPropertyRegistry) = TestContainer.configurePostgresProperties(registry)
    }

    @Autowired
    private lateinit var jwtTokenService: JwtTokenService

    @Autowired
    private lateinit var userService: UserService

    @Test
    fun `should not authenticate`() {
        val loginRequestDTO = LoginRequestDTO(username = "iamnotauser", password = "thisisnotapassword")

        jwtTokenService
            .authenticate(loginRequestDTO)
            .test()
            .expectError()
            .verify()
    }

    @Test
    fun `should succeed when login match user`() {
        val userRegistrationDTO =
            UserRegistrationDTO(email = "athena@poop.no", username = "athenapoop", password = "papauymamadeahbabythena")
        val loginRequestDTO = LoginRequestDTO(username = userRegistrationDTO.username!!, password = userRegistrationDTO.password)

        userService
            .register(userRegistrationDTO)
            .flatMap { jwtTokenService.authenticate(loginRequestDTO) }
            .test()
            .expectNextCount(1)
            .verifyComplete()
    }

    @Test
    fun `should generate token if authenticated`() {
        val userRegistrationDTO =
            UserRegistrationDTO(email = "athena@poopie.no", username = "athenapoopie", password = "verystrongpassword")
        val loginRequestDTO = LoginRequestDTO(username = userRegistrationDTO.username!!, password = userRegistrationDTO.password)
        userService
            .register(userRegistrationDTO)
            .flatMap { jwtTokenService.authenticate(loginRequestDTO) }
            .flatMap { jwtTokenService.generateToken(it) }
            .test()
            .expectNextCount(1)
            .verifyComplete()
    }
}
