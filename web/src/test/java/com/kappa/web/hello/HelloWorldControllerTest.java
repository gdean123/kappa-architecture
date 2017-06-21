package com.kappa.web.hello;

import com.kappa.web.kafka.TopicListener;
import com.kappa.web.kafka.TopicWriter;
import com.kappa.web.support.ControllerTestBase;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.kafka.listener.MessageListener;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class HelloWorldControllerTest extends ControllerTestBase {
    @Mock private TopicWriter topicWriter;
    @Mock private TopicListener topicListener;
    @Inject @InjectMocks private HelloWorldController helloWorldController;

    @Test
    public void create_writesHelloWorldToTheTopic() throws Exception {
        post("/");
        then(topicWriter).should().write("values-topic", 0, "Hello, World!");
    }

    @Captor
    private ArgumentCaptor<MessageListener<Integer, String>> argumentCaptor;

    @Test
    public void get_respondsWithEmittedValues() throws Exception {
        helloWorldController.listen();

        verify(topicListener).listen(eq("values-topic"), argumentCaptor.capture());
        argumentCaptor.getValue().onMessage(new ConsumerRecord<>(
                "values-topic", 0, 0, 0, "Hello, World!"
        ));

        String response = get("/");
        assertThat(response).isEqualTo("[\"Hello, World!\"]");
    }
}
