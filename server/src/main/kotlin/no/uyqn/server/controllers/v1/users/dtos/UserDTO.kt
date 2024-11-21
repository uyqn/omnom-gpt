package no.uyqn.server.controllers.v1.users.dtos

import no.uyqn.server.models.user.User
import no.uyqn.server.models.user.UserRole
import java.time.LocalDateTime

data class UserDTO(
    val id: Long,
    val email: String? = null,
    val username: String? = null,
    val password: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val roles: MutableCollection<out RoleDTO>,
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
                roles = roles.map { RoleDTO(authority = it.role) }.toMutableList(),
            )

        fun from(
            user: User,
            role: UserRole,
        ): UserDTO = from(user, listOf(role))
    }
}
