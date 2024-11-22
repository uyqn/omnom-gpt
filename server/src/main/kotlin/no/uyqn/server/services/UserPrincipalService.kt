package no.uyqn.server.services

import no.uyqn.server.models.UserPrincipal
import no.uyqn.server.repositories.UserRolesRepository
import no.uyqn.server.repositories.UsersRepository
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserPrincipalService(
    private val usersRepository: UsersRepository,
    private val userRolesRepository: UserRolesRepository,
) : ReactiveUserDetailsService {
    override fun findByUsername(username: String): Mono<UserDetails> =
        usersRepository
            .findByUsernameOrEmail(username)
            .switchIfEmpty(Mono.error(UsernameNotFoundException("User not found")))
            .flatMap { user ->
                userRolesRepository
                    .findByUserId(user?.id!!)
                    .collectList()
                    .map { roles -> UserPrincipal(user, roles) }
            }
}
