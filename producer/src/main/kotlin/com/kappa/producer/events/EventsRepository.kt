package com.kappa.producer.events

import com.kappa.producer.kafka.KafkaTopicWriter
import com.kappa.producer.sentences.UuidGenerator
import com.kappa.producer.serialization.CurrencySerializer
import org.springframework.stereotype.Component
import producer.ConsumerPlacedOrderKey
import producer.ConsumerPlacedOrderValue
import java.math.BigDecimal

@Component
class EventsRepository(
    private val kafkaTopicWriter: KafkaTopicWriter,
    private val uuidGenerator: UuidGenerator,
    private val currencySerializer: CurrencySerializer
) {
    fun consumerPlacedOrder(cost : BigDecimal) {
        kafkaTopicWriter.write(
            "consumer_placed_order",
            ConsumerPlacedOrderKey(uuidGenerator.generate()),
            ConsumerPlacedOrderValue(currencySerializer.serialize(cost))
        )
    }
}
