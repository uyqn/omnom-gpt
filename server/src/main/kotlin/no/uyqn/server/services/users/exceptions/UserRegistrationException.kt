package no.uyqn.server.services.users.exceptions

class UserRegistrationException(
    message: String,
) : RuntimeException(message) {
    companion object {
        val USER_ALREADY_EXISTS = UserRegistrationException("Username or email already taken")
    }
}
