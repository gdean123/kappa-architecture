package com.kappa.web.tweets

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class TweetsController(
    private val tweetRepository: TweetRepository
) {
    @PostMapping("/tweets")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody createTweetRequest: CreateTweetRequest): CreateTweetResponse {
        val tweet = Tweet(userId = createTweetRequest.userId, text = createTweetRequest.text)
        val createdTweet = tweetRepository.save(tweet)
        return CreateTweetResponse(createdTweet.id!!, createdTweet.userId, createdTweet.text)
    }
}