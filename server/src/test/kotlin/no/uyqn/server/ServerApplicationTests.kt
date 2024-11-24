package no.uyqn.server

import org.junit.jupiter.api.Test
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Container

@SpringBootIntegrationTest
class ServerApplicationTests {
    companion object {
        @Container
        private val postgreSQLContainer = TestContainer.postgreSQLContainer

        @JvmStatic
        @DynamicPropertySource
        fun configureProperties(registry: DynamicPropertyRegistry) = TestContainer.configurePostgresProperties(registry)
    }

    @Test
    fun contextLoads() {
    }
}
