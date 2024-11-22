package no.uyqn.server.exceptions.users

class UserRegistrationException(
    message: String,
) : RuntimeException(message) {
    companion object {
        val USER_ALREADY_EXISTS = UserRegistrationException("Username or email already taken")
    }
}