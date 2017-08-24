package com.kappa.web.support

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders

abstract class ControllerTestBase : SpringTestBase() {
    protected abstract fun controller(): Any

    protected fun get(url: String): String = perform(MockMvcRequestBuilders.get(url))

    protected fun post(url: String): String = perform(MockMvcRequestBuilders.post(url))

    private fun perform(get: MockHttpServletRequestBuilder?) = mvc().perform(get).andReturn().response.contentAsString

    private fun mvc() = MockMvcBuilders.standaloneSetup(controller()).build()
}