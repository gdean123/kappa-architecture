package com.kappa.web.tweets

data class CreateTweetResponse(
    val id: Long,
    val userId: Long,
    val text: String
)