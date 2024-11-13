package no.uyqn.omnomgpt

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class OmnomGptApplication

fun main(args: Array<String>) {
    val application = SpringApplication(OmnomGptApplication::class.java)

    application.addInitializers(DotenvLoader())

    application.run(*args)
}
