package com.kappa.web.follows

data class CreateFollowResponse(
    val id: Long,
    val followerId: Long,
    val followId: Long
)