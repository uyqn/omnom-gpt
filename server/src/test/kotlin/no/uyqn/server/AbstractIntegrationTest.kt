package no.uyqn.server

import no.uyqn.server.configurations.initializers.DotenvInitializer
import no.uyqn.server.configurations.initializers.OpenAiConfigurationInitializer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = [DotenvInitializer::class, OpenAiConfigurationInitializer::class])
abstract class AbstractIntegrationTest {
    companion object {
        @Container
        private val postgresTestContainer: PostgreSQLContainer<*> =
            PostgreSQLContainer("postgres:latest")
                .withDatabaseName("test")
                .withUsername("test")
                .withPassword("test")

        @JvmStatic
        @DynamicPropertySource
        fun configureProperties(registry: DynamicPropertyRegistry) {
            val url =
                "r2dbc:postgresql://" +
                    "${postgresTestContainer.host}:${postgresTestContainer.firstMappedPort}/" +
                    postgresTestContainer.databaseName

            registry.add("spring.r2dbc.url") { url }
            registry.add("spring.r2dbc.username") { postgresTestContainer.username }
            registry.add("spring.r2dbc.password") { postgresTestContainer.password }
        }
    }
}
