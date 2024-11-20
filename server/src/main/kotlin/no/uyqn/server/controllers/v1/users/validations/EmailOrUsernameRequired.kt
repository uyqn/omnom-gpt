package no.uyqn.server.controllers.v1.users.validations

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [EmailOrUsernameValidator::class])
@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class EmailOrUsernameRequired(
    val message: String = "Either email or username is required",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)
