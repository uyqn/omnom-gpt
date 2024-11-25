package no.uyqn.server.services

import no.uyqn.server.dtos.LoginRequestDTO
import no.uyqn.server.dtos.TokenDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class JwtTokenService(
    private val authenticationManager: ReactiveAuthenticationManager,
    private val encoder: JwtEncoder,
) {
    private val logger: Logger = LoggerFactory.getLogger(JwtTokenService::class.java)

    fun authenticate(loginRequestDTO: LoginRequestDTO): Mono<Authentication> {
        val authentication = UsernamePasswordAuthenticationToken(loginRequestDTO.username, loginRequestDTO.password)
        logger.info("Authenticated: $authentication")
        return authenticationManager.authenticate(authentication)
    }

    fun generateToken(
        authentication: Authentication,
        expiresAt: (Instant) -> Instant = { it.plus(15, ChronoUnit.MINUTES) },
    ): Mono<TokenDTO> {
        val now = Instant.now()
        val scope = authentication.authorities.joinToString(" ") { it.authority }

        val claims =
            JwtClaimsSet
                .builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(expiresAt(now))
                .subject(authentication.name)
                .claim("scope", scope)
                .build()
        val token = encoder.encode(JwtEncoderParameters.from(claims)).tokenValue

        return Mono.just(TokenDTO.from(claims, token))
    }
}
