package com.kappa.web.values;

import com.kappa.web.kafka.TopicWriter;
import com.kappa.web.support.ControllerTestBase;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;

public class ValuesControllerTest extends ControllerTestBase {
    @Mock private TopicWriter topicWriter;
    @Inject private ValueRepository valueRepository;
    @Inject @InjectMocks private ValuesController valuesController;

    @Test
    public void create_writesHelloWorldToTheTopic() throws Exception {
        post("/");
        then(topicWriter).should().write("values-topic", 0, "Hello, World!");
    }

    @Test
    public void get_returnsValuesFromTheDatabase() throws Exception {
        valueRepository.save(new Value("Hello, World!"));
        String response = get("/");
        assertThat(response).isEqualTo("[\"Hello, World!\"]");
    }
}