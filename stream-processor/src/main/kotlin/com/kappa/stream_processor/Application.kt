package com.kappa.stream_processor

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Application

fun main(arguments: Array<String>) {
    SpringApplication.run(Application::class.java, *arguments)
}
