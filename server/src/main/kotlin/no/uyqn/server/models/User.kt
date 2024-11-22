package no.uyqn.server.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("users")
data class User(
    @Id val id: Long? = null,
    @Column("email") val email: String?,
    @Column("username") val username: String?,
    @Column("password") val password: String,
    @Column("enabled") val enabled: Boolean = true,
    @Column("account_expired") val accountExpired: Boolean = false,
    @Column("account_locked") val accountLocked: Boolean = false,
    @Column("credentials_expired") val credentialsExpired: Boolean = false,
    @Column("created_at") val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column("updated_at") val updatedAt: LocalDateTime = LocalDateTime.now(),
)
