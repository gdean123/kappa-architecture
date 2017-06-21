package com.kappa.web.hello;

import com.kappa.web.kafka.TopicListener;
import com.kappa.web.kafka.TopicWriter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RestController
class HelloWorldController {
    private TopicWriter topicWriter;
    private TopicListener topicListener;
    private final CountDownLatch latch;
    private final List<String> receivedMessages;

    public HelloWorldController(TopicWriter topicWriter, TopicListener topicListener) {
        this.topicWriter = topicWriter;
        this.topicListener = topicListener;
        this.latch = new CountDownLatch(1);
        this.receivedMessages = new ArrayList<>();
    }

    @PostConstruct
    void listen() throws InterruptedException {
        topicListener.listen("values-topic", message -> {
            receivedMessages.add(message.value());
            latch.countDown();
        });
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    void create() throws InterruptedException {
        topicWriter.write("values-topic", 0, "Hello, World!");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    List<String> get() {
        return receivedMessages;
    }
}