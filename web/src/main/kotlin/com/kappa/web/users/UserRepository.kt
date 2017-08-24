package com.kappa.web.users

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component

@Component
interface UserRepository : CrudRepository<User, Long>