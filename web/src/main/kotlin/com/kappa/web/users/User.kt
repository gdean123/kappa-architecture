package com.kappa.web.users

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "users")
class User(
    var name: String = "",
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)