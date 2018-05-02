package com.kappa.stream_processor.serialization

import org.apache.avro.Conversions
import org.apache.avro.LogicalTypes
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class CurrencySerializerTest {
    private lateinit var currencySerializer: CurrencySerializer

    @Before
    fun setUp() {
        currencySerializer = CurrencySerializer()
    }

    @Test
    fun `#serialize writes a big decimal to a byte buffer`() {
        val serialized = currencySerializer.serialize(BigDecimal("12.34"))
        val deserialized = Conversions.DecimalConversion().fromBytes(serialized, null, LogicalTypes.decimal(10, 2))
        assertThat(deserialized).isEqualTo(BigDecimal("12.34"))
    }

    @Test
    fun `#deserialize reads a big decimal from a byte buffer`() {
        val serialized = Conversions.DecimalConversion().toBytes(BigDecimal("12.34"), null, LogicalTypes.decimal(10, 2))
        val deserialized = currencySerializer.deserialize(serialized)
        assertThat(deserialized).isEqualTo(BigDecimal("12.34"))
    }
}