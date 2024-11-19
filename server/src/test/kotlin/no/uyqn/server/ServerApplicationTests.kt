package no.uyqn.server

import no.uyqn.server.config.DotenvInitializer
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(initializers = [DotenvInitializer::class])
class ServerApplicationTests {
    @Test
    fun contextLoads() {
    }
}