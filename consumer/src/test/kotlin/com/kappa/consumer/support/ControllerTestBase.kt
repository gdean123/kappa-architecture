package com.kappa.consumer.support

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders

abstract class ControllerTestBase : SpringTestBase() {
    protected abstract fun controller(): Any

    protected fun get(url: String): HttpResponse = perform(
        MockMvcRequestBuilders.get(url)
    )

    private fun perform(requestBuilder: MockHttpServletRequestBuilder): HttpResponse {
        val response = mvc().perform(requestBuilder).andReturn().response
        val body = parseJson(response.contentAsString)!!
        return HttpResponse(response.status, body)
    }

    private fun mvc() = MockMvcBuilders.standaloneSetup(controller()).build()
}