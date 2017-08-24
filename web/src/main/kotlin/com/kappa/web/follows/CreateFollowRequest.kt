package com.kappa.web.follows

data class CreateFollowRequest(
    val followerId: Long,
    val followedId: Long
)
