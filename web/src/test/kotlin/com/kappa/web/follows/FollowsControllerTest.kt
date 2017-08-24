package com.kappa.web.follows

import com.kappa.web.support.ControllerTestBase
import com.kappa.web.users.User
import com.kappa.web.users.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import javax.inject.Inject

class FollowsControllerTest : ControllerTestBase() {
    @Inject private lateinit var followRepository: FollowRepository
    @Inject private lateinit var userRepository: UserRepository

    override fun controller() = FollowsController(followRepository)

    @Test
    fun `create saves a new follow to the database`() {
        val johnny = userRepository.save(User(name = "Johnny"))
        val june = userRepository.save(User(name = "June"))

        val response = post("/follows", hashMapOf(
            "followerId" to johnny.id!!,
            "followedId" to june.id!!
        ))

        assertThat(response.statusCode).isEqualTo(201)

        val followId = response.body?.get("id")?.asLong()
        val createdFollow: Follow = followRepository.findOne(followId)
        assertThat(createdFollow.followerId).isEqualTo(johnny.id!!)
        assertThat(createdFollow.followedId).isEqualTo(june.id!!)
    }
}