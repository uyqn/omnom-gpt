package no.uyqn.server.models.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class UserPrincipal(
    val user: User,
    val roles: MutableCollection<UserRole>,
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = roles.map { SimpleGrantedAuthority(it.role) }.toMutableList()

    override fun getPassword(): String = user.password

    override fun getUsername(): String = user.username ?: user.email!!

    override fun isAccountNonExpired(): Boolean = !user.accountExpired

    override fun isAccountNonLocked(): Boolean = !user.accountLocked

    override fun isCredentialsNonExpired(): Boolean = !user.credentialsExpired

    override fun isEnabled() = user.enabled
}
