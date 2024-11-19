package no.uyqn.server.configurations.initializers

import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.DotenvEntry
import io.github.cdimascio.dotenv.dotenv
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import nl.altindag.log.LogCaptor
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.context.ConfigurableApplicationContext
import kotlin.test.assertEquals

class DotenvInitializerTest {
    @MockK
    private lateinit var mockApplicationContext: ConfigurableApplicationContext

    @MockK
    private lateinit var mockDotenv: Dotenv

    private lateinit var logCaptor: LogCaptor

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        mockkStatic("io.github.cdimascio.dotenv.Dotenv")

        logCaptor = LogCaptor.forClass(DotenvInitializer::class.java)

        every {
            dotenv {
                directory = any(String::class)
                ignoreIfMissing = true
                ignoreIfMalformed = true
            }
        } returns mockDotenv
    }

    @Test
    fun `should load environment variables from the dotenv file in the root directory of the project`() {
        val mockEntries = mutableSetOf(DotenvEntry("key1", "value1"), DotenvEntry("key2", "value2"))
        every { mockDotenv.entries(Dotenv.Filter.DECLARED_IN_ENV_FILE) } returns mockEntries

        every { mockApplicationContext.environment.systemProperties } returns mutableMapOf()

        DotenvInitializer().initialize(mockApplicationContext)

        assertEquals("value1", mockApplicationContext.environment.systemProperties["key1"])
        assertEquals("value2", mockApplicationContext.environment.systemProperties["key2"])
    }

    @Test
    fun `should log a warning if no dotenv file is found`() {
        every { mockDotenv.entries(Dotenv.Filter.DECLARED_IN_ENV_FILE) } returns emptySet()

        DotenvInitializer().initialize(mockApplicationContext)

        assertEquals("No .env file found in ${System.getProperty("user.dir")}", logCaptor.warnLogs.first())
    }
}
