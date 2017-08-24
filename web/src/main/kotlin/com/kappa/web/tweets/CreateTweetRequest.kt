package com.kappa.web.tweets

data class CreateTweetRequest(
    val userId: Long,
    val text: String
)
