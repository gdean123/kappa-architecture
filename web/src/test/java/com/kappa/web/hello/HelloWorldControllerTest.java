package com.kappa.web.hello;

import com.kappa.web.kafka.KafkaClient;
import com.kappa.web.support.ControllerTestBase;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class HelloWorldControllerTest extends ControllerTestBase {
    @Mock private KafkaClient kafkaClient;
    @Inject @InjectMocks private HelloWorldController helloWorldController;

    @Test
    public void get_rendersHelloWorld() throws Exception {
        given(kafkaClient.getLastValue()).willReturn("Hello, World!");

        String response = get("/");

        then(kafkaClient).should().send("values-topic", "Hello, World!");
        assertThat(response).isEqualTo("Hello, World!");
    }
}
