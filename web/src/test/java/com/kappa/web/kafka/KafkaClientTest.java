package com.kappa.web.kafka;

import com.kappa.web.support.SpringTestBase;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.kafka.test.rule.KafkaEmbedded;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

public class KafkaClientTest extends SpringTestBase {
    @Inject private KafkaClient kafkaClient;

    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, "values-topic");

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.setProperty("spring.kafka.bootstrap-servers", embeddedKafka.getBrokersAsString());
    }

    @Test
    public void testSendAndReceive() throws Exception {
        kafkaClient.send("values-topic", "Hello, World!");
        assertThat(kafkaClient.getLastValue()).isEqualTo("Hello, World!");
    }
}