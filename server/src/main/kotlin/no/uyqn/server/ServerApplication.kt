package no.uyqn.server

import no.uyqn.server.configurations.initializers.DotenvInitializer
import no.uyqn.server.configurations.initializers.FlywayMigrateInitializer
import no.uyqn.server.configurations.initializers.OpenAiConfigurationInitializer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
class ServerApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder(ServerApplication::class.java)
        .initializers(DotenvInitializer())
        .initializers(FlywayMigrateInitializer())
        .initializers(OpenAiConfigurationInitializer())
        .run(*args)
}
