package com.kappa.web.values;

import com.kappa.web.kafka.TopicListener;
import com.kappa.web.support.SpringTestBase;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.kafka.listener.MessageListener;

import javax.inject.Inject;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class ValueListenerTest extends SpringTestBase {
    @Inject private ValueRepository valueRepository;
    @Mock private TopicListener topicListener;
    private ValueListener valueListener;

    @Before
    public void setup() {
        valueListener = new ValueListener(topicListener, valueRepository);
    }

    @Captor
    private ArgumentCaptor<MessageListener<Integer, String>> argumentCaptor;

    @Test
    public void listen_persistsTheValueToTheDatabase() throws InterruptedException {
        valueListener.listen();

        verify(topicListener).listen(eq("values-topic"), argumentCaptor.capture());
        argumentCaptor.getValue().onMessage(new ConsumerRecord<>(
            "values-topic", 0, 0, 0, "Hello, World!"
        ));

        ArrayList<Value> values = Lists.newArrayList(valueRepository.findAll());
        assertThat(values).hasSize(1);
        assertThat(values.get(0).getValue()).isEqualTo("Hello, World!");
    }
}
