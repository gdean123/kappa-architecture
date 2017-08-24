package com.kappa.web.values

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "values")
class Value(
    var value: String = "",
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null
)