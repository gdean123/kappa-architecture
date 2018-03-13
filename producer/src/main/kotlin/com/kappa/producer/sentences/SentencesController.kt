package com.kappa.producer.sentences

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class SentencesController(
    private val sentenceRepository: SentenceRepository
) {
    @PostMapping("/sentences")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun create(@RequestBody createSentenceRequest: CreateSentenceRequest) {
        sentenceRepository.create(createSentenceRequest.words)
    }
}
