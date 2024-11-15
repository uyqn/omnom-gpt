package no.uyqn.server

import no.uyqn.server.config.DotenvInitializer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
class ServerApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder(ServerApplication::class.java)
        .initializers(DotenvInitializer())
        .run(*args)
}
