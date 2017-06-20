package com.kappa.web.support;

import org.junit.Before;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

public abstract class ControllerTestBase extends SpringTestBase {
    @Inject private WebApplicationContext webApplicationContext;
    private MockMvc mvc;

    @Before
    public void controllerTestBaseSetup() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected String get(String url) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.get(url)).andReturn().getResponse().getContentAsString();
    }
}