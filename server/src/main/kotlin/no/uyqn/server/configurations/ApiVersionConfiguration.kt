package no.uyqn.server.configurations

import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.PathMatchConfigurer
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
class ApiVersionConfiguration : WebFluxConfigurer {
    override fun configurePathMatching(configurer: PathMatchConfigurer) {
        configurer.addPathPrefix("/api/v1") { it.packageName.startsWith("no.uyqn.server.controllers.v1") }
    }
}
