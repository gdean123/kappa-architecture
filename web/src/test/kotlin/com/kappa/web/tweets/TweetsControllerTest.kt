package com.kappa.web.tweets

import com.kappa.web.support.ControllerTestBase
import com.kappa.web.users.User
import com.kappa.web.users.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import javax.inject.Inject

class TweetsControllerTest : ControllerTestBase() {
    @Inject private lateinit var tweetRepository: TweetRepository
    @Inject private lateinit var userRepository: UserRepository

    override fun controller() = TweetsController(tweetRepository)

    @Test
    fun `create saves a new tweet to the database`() {
        val user = userRepository.save(User(name = "Johnny"))

        val response = post("/tweets", hashMapOf(
            "userId" to user.id!!,
            "text" to "I wanna be there on the stage with you"
        ))

        assertThat(response.statusCode).isEqualTo(201)

        val tweetId = response.body?.get("id")?.asLong()
        val createdTweet: Tweet = tweetRepository.findOne(tweetId)
        assertThat(createdTweet.userId).isEqualTo(user.id!!)
        assertThat(createdTweet.text).isEqualTo("I wanna be there on the stage with you")
    }
}