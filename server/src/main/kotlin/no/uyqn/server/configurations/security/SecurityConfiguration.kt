package no.uyqn.server.configurations.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfiguration {
    @Value("\${springdoc.swagger-ui.path}")
    private lateinit var springdocSwaggerUiPath: String

    @Value("\${springdoc.api-docs.path}")
    private lateinit var springdocApiDocsPath: String

    @Bean
    fun securityWebFilterChain(
        http: ServerHttpSecurity,
        authenticationManager: ReactiveAuthenticationManager,
    ): SecurityWebFilterChain {
        http
            .csrf { it.disable() }
            .authorizeExchange {
                it
                    .pathMatchers(
                        "/login",
                        "/api/*/users/register",
                        "/api/*/auth/login",
                        "/api/*/oauth2/login/**",
                        "/api-docs/**",
                        "/webjars/**",
                        springdocSwaggerUiPath,
                        springdocApiDocsPath,
                    ).permitAll()
                it.anyExchange().authenticated()
            }.formLogin { }
            .oauth2Login { }
            .oauth2ResourceServer { it.jwt {} }
        return http.build()
    }

    @Bean
    fun reactiveAuthenticationManager(
        userDetailsService: ReactiveUserDetailsService,
        passwordEncoder: PasswordEncoder,
    ): ReactiveAuthenticationManager {
        val authenticationManager = UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService)
        authenticationManager.setPasswordEncoder(passwordEncoder)
        return authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()
}
