package no.uyqn.server.dtos

import org.springframework.security.oauth2.jwt.JwtClaimsSet

data class TokenDTO(
    val token: String,
    val iss: String,
    val sub: String,
    val exp: Long,
    val iat: Long,
    val scope: String,
) {
    companion object {
        fun from(
            claims: JwtClaimsSet,
            token: String,
        ): TokenDTO =
            TokenDTO(
                token = token,
                iss = claims.getClaimAsString("iss"),
                sub = claims.subject,
                exp = claims.expiresAt.epochSecond,
                iat = claims.issuedAt.epochSecond,
                scope = claims.getClaimAsString("scope"),
            )
    }
}
