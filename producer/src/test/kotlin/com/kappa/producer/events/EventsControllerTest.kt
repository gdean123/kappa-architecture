package com.kappa.producer.events

import com.kappa.producer.support.ControllerTestBase
import com.nhaarman.mockito_kotlin.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import java.math.BigDecimal

class EventsControllerTest : ControllerTestBase() {
    private lateinit var eventsRepository: EventsRepository

    override fun controller() = EventsController(eventsRepository)

    @Before
    fun setUp() {
        eventsRepository = mock()
    }

    @Test
    fun `#consumerPlacedOrder emits the event to kafka`() {
        val response = post("/events/consumer_placed_order", hashMapOf("cost" to "12.34"))

        assertThat(response.statusCode).isEqualTo(202)
        verify(eventsRepository).consumerPlacedOrder(BigDecimal("12.34"))
    }
}