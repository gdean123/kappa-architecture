package com.kappa.consumer.support

import com.fasterxml.jackson.databind.JsonNode

data class HttpResponse(
    val statusCode: Int,
    val body: JsonNode?
)