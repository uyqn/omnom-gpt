package no.uyqn.server.configurations.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import java.security.KeyStore
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

@Configuration
class JwtKeystoreConfiguration(
    private val jwtKeystoreProperties: JwtKeystoreProperties,
) {
    @Value("\${keystore.location}")
    private lateinit var resource: Resource

    @Bean
    fun jwtKeystore(): JwtKeystore {
        val keystore = KeyStore.getInstance(jwtKeystoreProperties.type)
        keystore.load(resource.inputStream, jwtKeystoreProperties.password.toCharArray())
        return JwtKeystore(
            publicKey = keystore.getCertificate(jwtKeystoreProperties.alias).publicKey as RSAPublicKey,
            privateKey = keystore.getKey(jwtKeystoreProperties.alias, jwtKeystoreProperties.password.toCharArray()) as RSAPrivateKey,
        )
    }
}
