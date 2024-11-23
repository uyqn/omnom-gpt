package no.uyqn.server

import org.springframework.test.context.DynamicPropertyRegistry
import org.testcontainers.containers.PostgreSQLContainer

object TestContainer {
    val postgreSQLContainer =
        PostgreSQLContainer<Nothing>("postgres:17").apply {
            withDatabaseName("omnom")
            withUsername("test")
            withPassword("test")
            withReuse(true)
            start()
        }

    fun configurePostgresProperties(registry: DynamicPropertyRegistry) {
        registry.add("spring.r2dbc.url") {
            "r2dbc:postgresql://${postgreSQLContainer.host}:${postgreSQLContainer.firstMappedPort}/omnom"
        }
        registry.add("spring.r2dbc.username") { postgreSQLContainer.username }
        registry.add("spring.r2dbc.password") { postgreSQLContainer.password }

        registry.add("spring.flyway.url") {
            "jdbc:postgresql://${postgreSQLContainer.host}:${postgreSQLContainer.firstMappedPort}/omnom"
        }
        registry.add("spring.flyway.user") { postgreSQLContainer.username }
        registry.add("spring.flyway.password") { postgreSQLContainer.password }
    }
}
