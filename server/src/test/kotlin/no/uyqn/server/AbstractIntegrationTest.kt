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
import java.util.UUID

@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = [DotenvInitializer::class, OpenAiConfigurationInitializer::class])
abstract class AbstractIntegrationTest {
    companion object {
        private val logger = LoggerFactory.getLogger(AbstractIntegrationTest::class.java)

        private fun randomString() = UUID.randomUUID().toString()

        @Container
        private val postgresTestContainer: PostgreSQLContainer<*> =
            PostgreSQLContainer("postgres:latest")
                .withDatabaseName(randomString())
                .withUsername(randomString())
                .withPassword(randomString())

        @JvmStatic
        @DynamicPropertySource
        fun configureProperties(registry: DynamicPropertyRegistry) {
            val url =
                "r2dbc:postgresql://" +
                    "${postgresTestContainer.host}:${postgresTestContainer.firstMappedPort}/" +
                    postgresTestContainer.databaseName
            val username = postgresTestContainer.username
            val password = postgresTestContainer.password

            logger.info("registry spring.r2dbc.url: $url")
            registry.add("spring.r2dbc.url") { url }

            logger.info("registry spring.r2dbc.username: $username")
            registry.add("spring.r2dbc.username") { username }

            logger.info("registry spring.r2dbc.password: $password")
            registry.add("spring.r2dbc.password") { password }

            val flywayUrl = url.replace("r2dbc", "jdbc")
            logger.info("registry spring.flyway.url: $flywayUrl")
            registry.add("spring.flyway.url") { flywayUrl }

            logger.info("registry spring.flyway.user: $username")
            registry.add("spring.flyway.user") { username }

            logger.info("registry spring.flyway.password: $password")
            registry.add("spring.flyway.password") { password }

            logger.info("registry spring.flyway.driver-class-name: org.postgresql.Driver")
            registry.add("spring.driver-class-name") { "org.postgresql.Driver" }

            logger.info("registry spring.flyway.enabled: true")
            registry.add("spring.flyway.enabled") { true }

            logger.info("registry spring.flyway.baseline-on-migrate: true")
            registry.add("spring.flyway.baseline-on-migrate") { true }

            logger.info("registry spring.flyway.locations: classpath:db/migration")
            registry.add("spring.flyway.locations") { "classpath:db/migration" }
        }
    }
}
