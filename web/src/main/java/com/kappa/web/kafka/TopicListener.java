package com.kappa.web.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class TopicListener implements Closeable {
    private KafkaMessageListenerContainer<Integer, String> container;
    private String kafkaUrl;

    TopicListener(@Value("${kafka.url}") String kafkaUrl) {
        this.kafkaUrl = kafkaUrl;
    }

    public void listen(String topic, MessageListener<Integer, String> messageListener) throws InterruptedException {
        ContainerProperties containerProperties = new ContainerProperties(topic);
        containerProperties.setMessageListener(messageListener);
        container = createContainer(containerProperties, kafkaUrl);
        container.start();
        Thread.sleep(1000);
    }

    private KafkaMessageListenerContainer<Integer, String> createContainer(ContainerProperties containerProperties, String kafkaUrl) {
        Map<String, Object> properties = consumerProperties(kafkaUrl);
        DefaultKafkaConsumerFactory<Integer, String> consumerFactory =
                new DefaultKafkaConsumerFactory<>(properties);
        return new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
    }

    private Map<String, Object> consumerProperties(String kafkaUrl) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "some-group");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return properties;
    }

    @Override
    public void close() throws IOException {
        container.stop();
    }
}