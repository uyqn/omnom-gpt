package no.uyqn.server.configurations.initializers

import org.flywaydb.core.Flyway
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.get

class FlywayMigrateInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    private val logger = LoggerFactory.getLogger(FlywayMigrateInitializer::class.java)

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        logger.info("Migrating database")
        Flyway
            .configure()
            .dataSource(
                "jdbc:postgresql://localhost:5432/omnom",
                applicationContext.environment["POSTGRES_USER"],
                applicationContext.environment["POSTGRES_PASSWORD"],
            ).baselineOnMigrate(true)
            .schemas("public")
            .locations("classpath:db/migration")
            .load()
            .migrate()
    }
}
