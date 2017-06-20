package com.kappa.web.hello;

import com.kappa.web.kafka.KafkaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
class HelloWorldController {
    @Inject private KafkaClient kafkaClient;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    String get() throws InterruptedException {
        kafkaClient.send("values-topic", "Hello, World!");
        return kafkaClient.getLastValue();
    }
}