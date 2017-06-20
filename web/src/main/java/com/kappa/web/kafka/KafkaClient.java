package com.kappa.web.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Component
public class KafkaClient {
    @Inject private KafkaTemplate<String, String> kafkaTemplate;
    private String lastValue;
    private CountDownLatch latch = new CountDownLatch(1);

    public String getLastValue() throws InterruptedException {
        latch.await(10000, TimeUnit.MILLISECONDS);
        return lastValue;
    }

    public void send(String topic, String data) {
        kafkaTemplate.send(topic, data);
    }

    @KafkaListener(topics = "values-topic")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        lastValue = consumerRecord.value().toString();
        latch.countDown();
    }
}
