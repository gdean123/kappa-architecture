package com.kappa.web.follows

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component

@Component
interface FollowRepository : CrudRepository<Follow, Long>