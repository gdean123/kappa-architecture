package com.kappa.web.hello;

import com.kappa.web.support.ControllerTestBase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloWorldControllerTest extends ControllerTestBase {
    @Test
    public void get_rendersHelloWorld() throws Exception {
        String response = get("/");
        assertThat(response).isEqualTo("Hello, World!");
    }
}
