package com.kappa.web.support

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

fun parseJson(jsonString: String): JsonNode? = when {
    jsonString.isEmpty() -> null
    else -> ObjectMapper().readTree(jsonString)
}

fun writeJson(value: Any): String {
    return ObjectMapper().writeValueAsString(value)!!
}