package com.kappa.producer.events

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class EventsController(
    private val eventsRepository: EventsRepository
) {
    @PostMapping("events/consumer_placed_order")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun create(@RequestBody consumerPlacedOrderRequest: ConsumerPlacedOrderRequest) {
        eventsRepository.consumerPlacedOrder(consumerPlacedOrderRequest.cost)
    }
}