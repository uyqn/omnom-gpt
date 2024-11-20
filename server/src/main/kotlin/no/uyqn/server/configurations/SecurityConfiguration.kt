package no.uyqn.server.configurations

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.http
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfiguration {
    @Bean
    fun securityWebFilterChain(
        http: ServerHttpSecurity,
        authenticationManager: UserDetailsRepositoryReactiveAuthenticationManager,
    ): SecurityWebFilterChain {
        http
            .csrf { it.disable() }
            .authorizeExchange {
                it.pathMatchers("/api/v1/users/register").permitAll()
                it.anyExchange().authenticated()
            }.httpBasic { }
        return http.build()
    }

    @Bean
    fun reactiveAuthenticationManager(
        userDetailsService: ReactiveUserDetailsService,
        passwordEncoder: PasswordEncoder,
    ): UserDetailsRepositoryReactiveAuthenticationManager {
        val authenticationManager = UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService)
        authenticationManager.setPasswordEncoder(passwordEncoder)
        return authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()
}
