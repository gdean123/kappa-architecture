package com.kappa.web.values;

import com.google.common.collect.Streams;
import com.kappa.web.kafka.TopicWriter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
class ValuesController {
    private TopicWriter topicWriter;
    private ValueRepository valueRepository;

    public ValuesController(TopicWriter topicWriter, ValueRepository valueRepository) {
        this.topicWriter = topicWriter;
        this.valueRepository = valueRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    void create() throws InterruptedException {
        topicWriter.write("values-topic", 0, "Hello, World!");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    List<String> get() {
        return Streams.stream(valueRepository.findAll())
            .map(Value::getValue)
            .collect(Collectors.toList());
    }
}