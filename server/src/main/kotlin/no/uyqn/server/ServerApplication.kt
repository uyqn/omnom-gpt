package no.uyqn.server

import no.uyqn.server.configurations.initializers.DotenvInitializer
import no.uyqn.server.configurations.initializers.OpenAiConfigurationInitializer
import no.uyqn.server.configurations.security.JwtKeystoreProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.properties.EnableConfigurationProperties

@SpringBootApplication
@EnableConfigurationProperties(JwtKeystoreProperties::class)
class ServerApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder(ServerApplication::class.java)
        .initializers(DotenvInitializer())
        .initializers(OpenAiConfigurationInitializer())
        .run(*args)
}
