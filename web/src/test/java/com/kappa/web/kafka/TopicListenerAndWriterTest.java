package com.kappa.web.kafka;

import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.kafka.test.rule.KafkaEmbedded;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

public class TopicListenerAndWriterTest {
    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, "values-topic");

    @Test
    public void listenerReturnsWrittenValues() throws Exception {
        TopicWriter topicWriter = new TopicWriter(embeddedKafka.getBrokersAsString());
        TopicListener topicListener = new TopicListener(embeddedKafka.getBrokersAsString());

        final CountDownLatch latch = new CountDownLatch(2);
        final List<String> receivedMessages = new ArrayList<>();
        topicListener.listen("values-topic", message -> {
            receivedMessages.add(message.value());
            latch.countDown();
        });

        topicWriter.write("values-topic", 0, "foo");
        topicWriter.write("values-topic", 0, "bar");
        assertTrue(latch.await(60, TimeUnit.SECONDS));
        assertThat(receivedMessages.size()).isEqualTo(2);
        assertThat(receivedMessages.get(0)).isEqualTo("foo");
        assertThat(receivedMessages.get(1)).isEqualTo("bar");
    }
}
