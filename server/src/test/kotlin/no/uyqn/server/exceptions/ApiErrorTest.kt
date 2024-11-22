package no.uyqn.server.exceptions

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class ApiErrorTest {
    @Test
    fun `Test property accessor`() {
        val apiError =
            ApiError(
                status = HttpStatus.BAD_REQUEST,
                path = "/api/v1/users",
            )

        apiError.status shouldBe HttpStatus.BAD_REQUEST
        apiError.code shouldBe 400
        apiError.message shouldBe "Bad Request"
        apiError.path shouldBe "/api/v1/users"
    }
}
