package no.uyqn.server

import no.uyqn.server.configurations.initializers.DotenvInitializer
import no.uyqn.server.configurations.initializers.OpenAiConfigurationInitializer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.junit.jupiter.Testcontainers
import java.lang.annotation.Inherited

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = [DotenvInitializer::class, OpenAiConfigurationInitializer::class])
@Inherited
annotation class SpringBootIntegrationTest
