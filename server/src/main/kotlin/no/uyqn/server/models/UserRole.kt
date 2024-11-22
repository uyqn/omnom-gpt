package no.uyqn.server.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("user_roles")
data class UserRole(
    @Id val id: Long? = null,
    @Column("user_id") val userId: Long,
    @Column("role") val role: String,
    @Column("created_at") val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column("updated_at") val updatedAt: LocalDateTime = LocalDateTime.now(),
)
