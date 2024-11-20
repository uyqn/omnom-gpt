package no.uyqn.server.repositories.users

import no.uyqn.server.models.user.UserRole
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Flux

interface UserRolesRepository : R2dbcRepository<UserRole, Long> {
    fun findByUserId(userId: Long): Flux<UserRole>
}
