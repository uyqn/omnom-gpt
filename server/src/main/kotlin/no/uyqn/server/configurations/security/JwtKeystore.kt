package no.uyqn.server.configurations.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

@Component
data class JwtKeystore(
    @Autowired(required = false) val publicKey: RSAPublicKey,
    @Autowired(required = false) val privateKey: RSAPrivateKey,
)
