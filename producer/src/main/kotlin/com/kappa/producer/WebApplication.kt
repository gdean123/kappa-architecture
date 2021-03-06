package com.kappa.producer

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class WebApplication

fun main(arguments: Array<String>) {
    SpringApplication.run(WebApplication::class.java, *arguments)
}
