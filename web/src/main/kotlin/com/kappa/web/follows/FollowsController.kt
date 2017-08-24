package com.kappa.web.follows

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class FollowsController(
    private val followRepository: FollowRepository
) {
    @PostMapping("/follows")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody createFollowRequest: CreateFollowRequest): CreateFollowResponse {
        val follow = Follow(followerId = createFollowRequest.followerId, followedId = createFollowRequest.followedId)
        val createdFollow = followRepository.save(follow)
        return CreateFollowResponse(createdFollow.id!!, createdFollow.followerId, createdFollow.followedId)
    }
}