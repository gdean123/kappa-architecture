package com.kappa.web

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class WebApplication

fun main(arguments: Array<String>) {
    SpringApplication.run(WebApplication::class.java, *arguments)
}
