package no.uyqn.server.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
class SecurityConfiguration {
    @Value("\${env.redirect.url}")
    private lateinit var redirectUrl: String

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize(anyRequest, authenticated)
            }
            formLogin { disable() }
            logout { permitAll() }
            exceptionHandling {
                authenticationEntryPoint =
                    AuthenticationEntryPoint { _, response, _ ->
                        response.sendRedirect(redirectUrl)
                    }
            }
        }
        return http.build()
    }
}
