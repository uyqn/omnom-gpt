package no.uyqn.server.models.user

import org.springframework.security.core.GrantedAuthority

enum class Role : GrantedAuthority {
    USER,
    ADMIN,
    HOUSEHOLD_OWNER,
    HOUSEHOLD_MEMBER,
    ;

    override fun getAuthority(): String = name
}
