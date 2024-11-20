package no.uyqn.server.repositories.users

import no.uyqn.server.models.user.User
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Mono

interface UsersRepository : R2dbcRepository<User, Long> {
    @Query("SELECT * FROM users WHERE username = :usernameOrEmail OR email = :usernameOrEmail")
    fun findByUsernameOrEmail(usernameOrEmail: String): Mono<User?>
}
