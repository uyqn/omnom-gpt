package no.uyqn.server.configurations.security

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "keystore")
data class JwtKeystoreProperties(
    val location: String,
    val password: String,
    val alias: String,
    val keyPassword: String,
    val type: String,
)
