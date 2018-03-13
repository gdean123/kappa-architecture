package com.kappa.producer.sentences

import org.springframework.stereotype.Component
import java.util.*

@Component
class UuidGenerator {
    fun generate() = UUID.randomUUID().toString()
}