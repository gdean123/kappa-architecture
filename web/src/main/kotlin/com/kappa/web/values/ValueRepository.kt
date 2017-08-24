package com.kappa.web.values

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component

@Component
interface ValueRepository : CrudRepository<Value, Long>