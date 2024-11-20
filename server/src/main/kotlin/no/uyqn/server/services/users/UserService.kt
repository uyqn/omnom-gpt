package no.uyqn.server.services.users

import no.uyqn.server.controllers.v1.users.dtos.UserDTO
import no.uyqn.server.controllers.v1.users.dtos.UserRegistrationDTO
import reactor.core.publisher.Mono

interface UserService {
    fun register(userRegistrationDTO: UserRegistrationDTO): Mono<UserDTO>
}
