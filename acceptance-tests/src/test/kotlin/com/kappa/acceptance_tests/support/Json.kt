package com.kappa.acceptance_tests.support

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.apache.http.HttpResponse

object Json {
    inline fun <reified T : Any> parse(response: HttpResponse): T =
        jacksonObjectMapper().readValue(response.entity.content)

    fun write(objVal: Any): String {
        return jacksonObjectMapper().writeValueAsString(objVal)
    }
}