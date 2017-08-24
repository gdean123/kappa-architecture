package com.kappa.web.users

import com.kappa.web.support.ControllerTestBase
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import javax.inject.Inject

class UsersControllerTest : ControllerTestBase() {
    @Inject private lateinit var userRepository: UserRepository

    override fun controller() = UsersController(userRepository)

    @Test
    fun `create saves a new user to the database`() {
        val response = post("/users", hashMapOf("name" to "Johnny"))
        assertThat(response.statusCode).isEqualTo(200)

        val userId = response.body?.get("id")?.asLong()
        assertThat(userRepository.findOne(userId).name).isEqualTo("Johnny")
    }
}