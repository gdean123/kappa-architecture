package com.kappa.consumer.word_counts

import org.springframework.web.bind.annotation.*

@RestController
class WordCountsController {
    @GetMapping("/word_counts")
    fun get(@RequestBody getWordCountRequest: GetWordCountRequest) {
        println("Get word count request made")
    }
}