package com.kappa.web.users

import org.springframework.web.bind.annotation.*

@RestController
class UsersController(
    private val userRepository: UserRepository
) {
    @PostMapping("/users")
    fun create(@RequestBody createUserRequest: CreateUserRequest): CreateUserResponse {
        val user = User(createUserRequest.name)
        val savedUser = userRepository.save(user)
        return CreateUserResponse(savedUser.name, savedUser.id!!)
    }
}