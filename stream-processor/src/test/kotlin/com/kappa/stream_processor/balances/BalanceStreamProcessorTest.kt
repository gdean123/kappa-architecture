package com.kappa.stream_processor.balances

import com.kappa.stream_processor.serialization.CurrencySerializer
import com.kappa.stream_processor.support.StreamTestBase
import org.apache.kafka.streams.StreamsBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import producer.ConsumerPlacedOrderKey
import producer.ConsumerPlacedOrderValue
import stream_processor.ConsumerBalanceKey
import stream_processor.ConsumerBalanceValue
import java.math.BigDecimal

class BalanceStreamProcessorTest : StreamTestBase() {
    private lateinit var currencySerializer: CurrencySerializer

    override fun stream(streamBuilder: StreamsBuilder) {
        currencySerializer = CurrencySerializer()
        BalanceStreamProcessor(streamBuilder, currencySerializer).stream()
    }

    @Test
    fun `#stream computes running balances`() {
        emit("consumer_placed_order", ConsumerPlacedOrderKey("first-id"), ConsumerPlacedOrderValue(twentyTwoFiftyFive()))
        emit("consumer_placed_order", ConsumerPlacedOrderKey("second-id"), ConsumerPlacedOrderValue(fiftyFiveThirtyThree()))

        assertThat(all<ConsumerBalanceKey, ConsumerBalanceValue>(2, "consumer_balances")).isEqualTo(arrayOf(
            ConsumerBalanceKey("unknown-user-id") to ConsumerBalanceValue(twentyTwoFiftyFive()),
            ConsumerBalanceKey("unknown-user-id") to ConsumerBalanceValue(seventySevenEightyEight())
        ))
    }

    private fun seventySevenEightyEight() = currencySerializer.serialize(BigDecimal("77.88"))
    private fun fiftyFiveThirtyThree() = currencySerializer.serialize(BigDecimal("55.33"))
    private fun twentyTwoFiftyFive() = currencySerializer.serialize(BigDecimal("22.55"))
}
