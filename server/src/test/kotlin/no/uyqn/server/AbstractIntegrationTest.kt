package no.uyqn.server

import io.kotest.core.spec.style.DescribeSpec
import no.uyqn.server.configurations.initializers.DotenvInitializer
import no.uyqn.server.configurations.initializers.OpenAiConfigurationInitializer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.userdetails.User.withUsername
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = [DotenvInitializer::class, OpenAiConfigurationInitializer::class])
abstract class AbstractIntegrationTest : DescribeSpec() {
    companion object {
        @Container
        private val postgresContainer =
            PostgreSQLContainer<Nothing>("postgres:17").apply {
                withDatabaseName("omnom")
                withUsername("test")
                withPassword("test")
                withReuse(true)
                start()
            }

        @JvmStatic
        @DynamicPropertySource
        fun configureProperties(registry: org.springframework.test.context.DynamicPropertyRegistry) {
            registry.add("spring.r2dbc.url") {
                "r2dbc:postgresql://${postgresContainer.host}:${postgresContainer.firstMappedPort}/omnom"
            }
            registry.add("spring.r2dbc.username") { postgresContainer.username }
            registry.add("spring.r2dbc.password") { postgresContainer.password }

            registry.add("spring.flyway.url") {
                "jdbc:postgresql://${postgresContainer.host}:${postgresContainer.firstMappedPort}/omnom"
            }
            registry.add("spring.flyway.user") { postgresContainer.username }
            registry.add("spring.flyway.password") { postgresContainer.password }
        }
    }
}
