package com.kappa.web.values;

import com.kappa.web.kafka.TopicListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Component
class ValueListener {
    private final TopicListener topicListener;
    private final ValueRepository valueRepository;

    ValueListener(TopicListener topicListener, ValueRepository valueRepository) {
        this.topicListener = topicListener;
        this.valueRepository = valueRepository;
    }

    @PostConstruct
    void listen() throws InterruptedException {
        topicListener.listen("values-topic", message -> {
            valueRepository.save(new Value(message.value()));
        });
    }
}