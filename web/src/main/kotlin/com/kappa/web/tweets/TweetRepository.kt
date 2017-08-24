package com.kappa.web.tweets

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component

@Component
interface TweetRepository : CrudRepository<Tweet, Long>