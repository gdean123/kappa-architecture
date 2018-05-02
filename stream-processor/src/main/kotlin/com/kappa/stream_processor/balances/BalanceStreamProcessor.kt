package com.kappa.stream_processor.balances

import com.kappa.stream_processor.serialization.CurrencySerializer
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.StreamsBuilder
import org.springframework.stereotype.Component
import producer.ConsumerPlacedOrderKey
import producer.ConsumerPlacedOrderValue
import stream_processor.ConsumerBalanceKey
import stream_processor.ConsumerBalanceValue
import javax.annotation.PostConstruct

@Component
class BalanceStreamProcessor(
    private val streamsBuilder: StreamsBuilder,
    private val currencySerializer: CurrencySerializer
) {
    @PostConstruct
    fun stream() {
        val source = streamsBuilder.stream<ConsumerPlacedOrderKey, ConsumerPlacedOrderValue>("consumer_placed_order")

        val balance = source
            .peek { key, value -> println("Processing $key: $value") }
            .map { _, consumerPlacedOrderValue -> KeyValue(ConsumerBalanceKey("unknown-user-id"), ConsumerBalanceValue(consumerPlacedOrderValue.getCost())) }
            .groupBy({ consumerBalanceKey, _ -> consumerBalanceKey })
            .reduce { consumerBalanceValue, consumerBalanceAccumulator -> sum(consumerBalanceValue, consumerBalanceAccumulator) }
            .toStream()
            .peek { key, value -> println("Emitting $key: $value") }

        balance.to("consumer_balances")
    }

    private fun sum(consumerBalanceValue: ConsumerBalanceValue, consumerBalanceAccumulator: ConsumerBalanceValue): ConsumerBalanceValue {
        val firstBalance = currencySerializer.deserialize(consumerBalanceValue.getBalance())
        val secondBalance = currencySerializer.deserialize(consumerBalanceAccumulator.getBalance())
        val total = currencySerializer.serialize(firstBalance + secondBalance)

        return ConsumerBalanceValue(total)
    }
}