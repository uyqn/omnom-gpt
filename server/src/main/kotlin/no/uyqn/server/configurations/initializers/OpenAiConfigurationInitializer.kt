package no.uyqn.server.configurations.initializers

import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.get

class OpenAiConfigurationInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    private val logger = LoggerFactory.getLogger(OpenAiConfigurationInitializer::class.java)

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        logger.info("Setting OpenAI configuration")
        val openAiKey = applicationContext.environment["OPENAI_API_KEY"] ?: throw IllegalArgumentException("OPENAI_API_KEY is required")

        if (openAiKey.startsWith("sk-")) {
            logger.info("OpenAI API key identified")
            applicationContext.environment.systemProperties["spring.ai.azure.openai.openai-api-key"] = openAiKey
            return
        }

        logger.info("Azure OpenAI API key identified")
        val openAiResource =
            applicationContext.environment["OPENAI_RESOURCE"]
                ?: throw IllegalArgumentException("OPENAI_RESOURCE is required")

        applicationContext.environment.systemProperties["spring.ai.azure.openai.api-key"] = openAiKey
        applicationContext.environment.systemProperties["spring.ai.azure.openai.endpoint"] = "https://$openAiResource.openai.azure.com/"
    }
}
