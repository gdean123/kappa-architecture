package com.kappa.producer.serialization

import org.apache.avro.Conversions
import org.apache.avro.LogicalTypes
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.nio.ByteBuffer

@Component
class CurrencySerializer {
    fun serialize(value: BigDecimal) : ByteBuffer {
        return Conversions.DecimalConversion().toBytes(value, null, currencyType())
    }

    fun deserialize(serializedValue: ByteBuffer): BigDecimal {
        return Conversions.DecimalConversion().fromBytes(serializedValue, null, currencyType())
    }

    private fun currencyType() = LogicalTypes.decimal(10, 2)
}