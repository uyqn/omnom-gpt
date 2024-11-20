package no.uyqn.server.controllers.v1.users.dtos

import no.uyqn.server.models.user.User
import no.uyqn.server.models.user.UserRole
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.time.LocalDateTime

data class UserDTO(
    val id: Long,
    val email: String? = null,
    val username: String? = null,
    val password: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val roles: MutableCollection<out GrantedAuthority>,
) {
    companion object {
        fun from(
            user: User,
            roles: Collection<UserRole>,
        ): UserDTO =
            UserDTO(
                id = user.id!!,
                email = user.email,
                username = user.username,
                password = user.password,
                createdAt = user.createdAt,
                updatedAt = user.updatedAt,
                roles = roles.map { SimpleGrantedAuthority(it.role) }.toMutableList(),
            )

        fun from(
            user: User,
            role: UserRole,
        ): UserDTO = from(user, listOf(role))
    }
}
