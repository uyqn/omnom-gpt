package no.uyqn.server.config

import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import java.io.File

/**
 * Loads environment variables from the .env file in the root directory of the project.
 */
class DotenvInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    private val logger = LoggerFactory.getLogger(DotenvInitializer::class.java)

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        val rootDirectory = File(System.getProperty("user.dir"))
        val dotenv =
            dotenv {
                directory = if (rootDirectory.name == "server") rootDirectory.parent else rootDirectory.path
                ignoreIfMissing = true
                ignoreIfMalformed = true
            }

        val entries = dotenv.entries(Dotenv.Filter.DECLARED_IN_ENV_FILE)
        if (entries.isEmpty()) {
            logger.warn("No .env file found in $rootDirectory")
            return
        }

        entries.forEach {
            logger.info("Setting environment variable ${it.key} to ${it.value}")
            applicationContext.environment.systemProperties[it.key] = it.value
        }
    }
}
