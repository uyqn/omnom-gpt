package no.uyqn.server.services

import no.uyqn.server.dtos.UserDTO
import no.uyqn.server.dtos.UserRegistrationDTO
import no.uyqn.server.exceptions.UserRegistrationException
import no.uyqn.server.models.Role
import no.uyqn.server.models.User
import no.uyqn.server.models.UserRole
import no.uyqn.server.repositories.UserRolesRepository
import no.uyqn.server.repositories.UsersRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Primary
@Service
class UserService(
    private val usersRepository: UsersRepository,
    private val userRolesRepository: UserRolesRepository,
) {
    fun register(userRegistrationDTO: UserRegistrationDTO): Mono<UserDTO> =
        usersRepository
            .findByUsernameOrEmail(userRegistrationDTO.username ?: userRegistrationDTO.email!!)
            .flatMap { Mono.error<User>(UserRegistrationException.USER_ALREADY_EXISTS) }
            .switchIfEmpty {
                val user = saveUser(userRegistrationDTO)
                println(user)
                user
            }.flatMap {
                saveUserRole(UserRole(userId = it.id!!, role = Role.USER.authority)).flatMap { userRole ->
                    Mono.just(UserDTO.from(it, mutableListOf(userRole)))
                }
            }

    private fun saveUser(user: User): Mono<User> = Mono.defer { usersRepository.save(user) }

    private fun saveUser(userRegistrationDTO: UserRegistrationDTO): Mono<User> =
        saveUser(User(email = userRegistrationDTO.email, username = userRegistrationDTO.username, password = userRegistrationDTO.password))

    private fun saveUserRole(userRole: UserRole): Mono<UserRole> =
        Mono.defer {
            userRolesRepository.save(userRole)
        }
}
