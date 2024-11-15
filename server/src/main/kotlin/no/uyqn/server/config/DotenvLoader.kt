package no.uyqn.server.config

import io.github.cdimascio.dotenv.Dotenv
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext

class DotenvLoader : ApplicationContextInitializer<ConfigurableApplicationContext> {
    private val logger = LoggerFactory.getLogger(DotenvLoader::class.java)

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        val dotenv =
            Dotenv
                .configure()
                .ignoreIfMissing()
                .load()
        val entries = dotenv.entries(Dotenv.Filter.DECLARED_IN_ENV_FILE)

        if (entries.isEmpty()) {
            logger.warn("No .env file found in ${System.getProperty("user.dir")}")
            return
        }

        entries.forEach {
            logger.info("Setting environment variable ${it.key} to ${it.value}")
            System.setProperty(it.key, it.value)
        }
    }
}
