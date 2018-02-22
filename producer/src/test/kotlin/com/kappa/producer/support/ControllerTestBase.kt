package com.kappa.producer.support

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders

abstract class ControllerTestBase : SpringTestBase() {
    protected abstract fun controller(): Any

    protected fun post(url: String, body: HashMap<String, Any>): HttpResponse = perform(
        MockMvcRequestBuilders.post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeJson(body))
    )

    private fun perform(requestBuilder: MockHttpServletRequestBuilder): HttpResponse {
        val response = mvc().perform(requestBuilder).andReturn().response
        val body = parseJson(response.contentAsString)
        return HttpResponse(response.status, body)
    }

    private fun mvc() = MockMvcBuilders.standaloneSetup(controller()).build()
}