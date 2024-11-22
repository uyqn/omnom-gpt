package no.uyqn.server.repositories

import no.uyqn.server.models.UserRole
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Flux

interface UserRolesRepository : R2dbcRepository<UserRole, Long> {
    fun findByUserId(userId: Long): Flux<UserRole>
}
