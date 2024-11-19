package no.uyqn.server.configurations.initializers

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import nl.altindag.log.LogCaptor
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.ConfigurableEnvironment
import kotlin.test.assertEquals

class OpenAiConfigurationInitializerTest {
    @MockK
    private lateinit var mockApplicationContext: ConfigurableApplicationContext

    @MockK
    private lateinit var mockEnvironment: ConfigurableEnvironment

    private lateinit var logCaptor: LogCaptor

    private val initializer = OpenAiConfigurationInitializer()

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        every { mockApplicationContext.environment } returns mockEnvironment
        logCaptor = LogCaptor.forClass(OpenAiConfigurationInitializer::class.java)
        every { mockEnvironment.systemProperties } returns mutableMapOf()
    }

    @Test
    fun `should set OpenAI API key when key starts with sk-`() {
        every { mockEnvironment.getProperty("OPENAI_API_KEY") } returns "sk-someOpenAiKey"

        initializer.initialize(mockApplicationContext)

        assertEquals("sk-someOpenAiKey", mockEnvironment.systemProperties["spring.ai.azure.openai.openai-api-key"])
    }

    @Test
    fun `should set Azure OpenAI API key when key does not starts with sk-`() {
        every { mockEnvironment.getProperty("OPENAI_API_KEY") } returns "someAzureKey"
        every { mockEnvironment.getProperty("OPENAI_RESOURCE") } returns "some-azure-resource"

        initializer.initialize(mockApplicationContext)

        assertEquals("someAzureKey", mockEnvironment.systemProperties["spring.ai.azure.openai.api-key"])
        assertEquals("https://some-azure-resource.openai.azure.com/", mockEnvironment.systemProperties["spring.ai.azure.openai.endpoint"])
    }

    @Test
    fun `should throw IllegalArgumentException when OPENAI_API_KEY is missing`() {
        every { mockEnvironment.getProperty("OPENAI_API_KEY") } returns null
        assertThrows<IllegalArgumentException> { initializer.initialize(mockApplicationContext) }
    }

    @Test
    fun `should throw IllegalArgumentException when OPENAI_RESOURCE is missing`() {
        every { mockEnvironment.getProperty("OPENAI_API_KEY") } returns "someAzureKey"
        every { mockEnvironment.getProperty("OPENAI_RESOURCE") } returns null
        assertThrows<IllegalArgumentException> { initializer.initialize(mockApplicationContext) }
    }
}
