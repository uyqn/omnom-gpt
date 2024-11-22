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
                applicationContext.environment["spring.r2dbc.url"],
                applicationContext.environment["spring.r2dbc.username"],
                applicationContext.environment["spring.r2dbc.password"],
            ).baselineOnMigrate(true)
            .schemas("public")
            .locations("classpath:db/migration")
            .load()
            .migrate()
    }
}
