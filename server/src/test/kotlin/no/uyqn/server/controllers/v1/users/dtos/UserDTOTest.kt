package no.uyqn.server.controllers.v1.users.dtos

import io.kotest.matchers.shouldBe
import no.uyqn.server.dtos.RoleDTO
import no.uyqn.server.dtos.UserDTO
import no.uyqn.server.models.Role
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class UserDTOTest {
    @Test
    fun `Test property accessor`() {
        val date = LocalDate.of(2022, 9, 1)
        val time = LocalTime.of(19, 1)
        val dateTime = LocalDateTime.of(date, time)
        val userDTO =
            UserDTO(
                id = 1,
                email = "athena@poop.no",
                username = "athenapoop",
                password = "kakemin123mø",
                roles = mutableListOf(RoleDTO(Role.ADMIN.authority)),
                createdAt = dateTime,
                updatedAt = dateTime,
            )

        userDTO.id shouldBe 1
        userDTO.email shouldBe "athena@poop.no"
        userDTO.username shouldBe "athenapoop"
        userDTO.password shouldBe "kakemin123mø"
        userDTO.roles shouldBe mutableListOf(RoleDTO(Role.ADMIN.authority))
        userDTO.createdAt shouldBe dateTime
        userDTO.updatedAt shouldBe dateTime
    }
}
