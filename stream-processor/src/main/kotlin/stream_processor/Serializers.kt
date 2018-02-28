package stream_processor

import io.confluent.kafka.streams.serdes.avro.SpecificAvroDeserializer
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerializer
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes

class Serializers(private val schemaRegistryUrl: String) {
    fun <T : SpecificRecord> key(): Serde<T> = serializer(true)
    fun <T : SpecificRecord> value(): Serde<T> = serializer(false)
    fun string(): Serde<String> = Serdes.String()

    private fun <T : SpecificRecord> serializer(isKey: Boolean): Serde<T> {
        val valueSerde = Serdes.serdeFrom(SpecificAvroSerializer<T>(), SpecificAvroDeserializer<T>())
        valueSerde.configure(mapOf("schema.registry.url" to schemaRegistryUrl), isKey)
        return valueSerde
    }
}