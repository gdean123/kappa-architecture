package com.kappa.producer.support

import com.fasterxml.jackson.databind.JsonNode

data class HttpResponse(
    val statusCode: Int,
    val body: JsonNode?
)