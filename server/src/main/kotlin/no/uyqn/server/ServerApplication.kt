package no.uyqn.server

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ServerApplication

fun main(args: Array<String>) {
    val application = SpringApplication(ServerApplication::class.java)
    application.addInitializers(DotenvLoader())
    application.run(*args)
}
