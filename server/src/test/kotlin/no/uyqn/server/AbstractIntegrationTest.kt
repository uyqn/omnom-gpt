package no.uyqn.server

import no.uyqn.server.configurations.initializers.DotenvInitializer
import no.uyqn.server.configurations.initializers.OpenAiConfigurationInitializer
import org.slf4j.LoggerFactory
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
        private val logger = LoggerFactory.getLogger(AbstractIntegrationTest::class.java)

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
            val username = postgresTestContainer.username
            val password = postgresTestContainer.password

            logger.debug("Setting up test properties")
            logger.debug("URL: $url")
            logger.debug("Username: $username")
            logger.debug("Password: $password")

            registry.add("spring.r2dbc.url") { url }
            registry.add("spring.r2dbc.username") { postgresTestContainer.username }
            registry.add("spring.r2dbc.password") { postgresTestContainer.password }

            logger.debug("Migrating database")
            registry.add("spring.flyway.url") { url.replace("r2dbc", "jdbc") }
            registry.add("spring.flyway.user") { username }
            registry.add("spring.flyway.password") { password }
            registry.add("spring.driver-class-name") { "org.postgresql.Driver" }
            registry.add("spring.flyway.enabled") { true }
            registry.add("spring.flyway.baseline-on-migrate") { true }
            registry.add("spring.flyway.locations") { "classpath:db/migration" }
        }
    }
}
