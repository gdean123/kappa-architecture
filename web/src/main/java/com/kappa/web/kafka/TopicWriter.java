package com.kappa.web.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TopicWriter {
    private String kafkaUrl;

    TopicWriter(@Value("${kafka.url}") String kafkaUrl) {
        this.kafkaUrl = kafkaUrl;
    }

    public void write(String topic, Integer key, String value) {
        KafkaTemplate<Integer, String> template = createTemplate(kafkaUrl);
        template.setDefaultTopic(topic);
        template.sendDefault(key, value);
        template.flush();
    }

    private KafkaTemplate<Integer, String> createTemplate(String kafkaUrl) {
        Map<String, Object> senderProperties = senderProperties(kafkaUrl);
        ProducerFactory<Integer, String> producerFactory = new DefaultKafkaProducerFactory<>(senderProperties);
        return new KafkaTemplate<>(producerFactory);
    }

    private Map<String, Object> senderProperties(String kafkaUrl) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);
        properties.put(ProducerConfig.RETRIES_CONFIG, 0);
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return properties;
    }
}
