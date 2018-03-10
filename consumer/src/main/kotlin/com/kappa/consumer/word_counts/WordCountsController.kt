package com.kappa.consumer.word_counts

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class WordCountsController(
    private val wordCountRepository: WordCountRepository
) {
    @GetMapping("/word_counts/{word}")
    fun get(@PathVariable("word") word: String): WordCountResponse {
        val wordCount = wordCountRepository.get(word)
        return WordCountResponse(word, wordCount)
    }
}
