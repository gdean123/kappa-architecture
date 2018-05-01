package com.kappa.producer.events

import com.kappa.producer.kafka.KafkaTopicWriter
import com.kappa.producer.sentences.UuidGenerator
import com.kappa.producer.serialization.CurrencySerializer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.apache.avro.Conversions
import org.apache.avro.LogicalTypes
import org.junit.Before
import org.junit.Test
import producer.ConsumerPlacedOrderKey
import producer.ConsumerPlacedOrderValue
import java.math.BigDecimal

class EventsRepositoryTest {
    private lateinit var kafkaTopicWriter: KafkaTopicWriter
    private lateinit var eventsRepository: EventsRepository
    private lateinit var uuidGenerator: UuidGenerator
    private lateinit var currencySerializer: CurrencySerializer

    @Before
    fun setUp() {
        uuidGenerator = mock()
        kafkaTopicWriter = mock()
        currencySerializer = CurrencySerializer()
        eventsRepository = EventsRepository(kafkaTopicWriter, uuidGenerator, currencySerializer)
    }

    @Test
    fun `#consumerPlacedOrder emits the cost on the consumer_placed_order topic`() {
        whenever(uuidGenerator.generate()).thenReturn("some-uuid")
        eventsRepository.consumerPlacedOrder(BigDecimal("12.34"))

        val bytes = Conversions.DecimalConversion().toBytes(BigDecimal("12.34"), null, LogicalTypes.decimal(10, 2))

        verify(kafkaTopicWriter).write(
            "consumer_placed_order",
            ConsumerPlacedOrderKey("some-uuid"),
            ConsumerPlacedOrderValue(bytes)
        )
    }
}