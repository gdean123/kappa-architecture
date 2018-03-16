package com.kappa.acceptance_tests.support

data class JsonResponse<out T> (
    val status: Int,
    val body: T
)