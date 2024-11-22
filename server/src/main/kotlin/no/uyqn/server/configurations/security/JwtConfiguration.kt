package no.uyqn.server.configurations.security

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import org.bouncycastle.asn1.x509.ObjectDigestInfo.publicKey
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder

@Configuration
class JwtConfiguration(
    private val jwtKeystore: JwtKeystore,
) {
    @Bean
    fun jwtDecoder(): ReactiveJwtDecoder = NimbusReactiveJwtDecoder.withPublicKey(jwtKeystore.publicKey).build()

    @Bean
    fun jwtEncoder(): JwtEncoder {
        val jwk =
            RSAKey
                .Builder(jwtKeystore.publicKey)
                .privateKey(jwtKeystore.privateKey)
                .build()
        return NimbusJwtEncoder(ImmutableJWKSet(JWKSet(jwk)))
    }
}
