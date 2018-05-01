package com.kappa.producer.events

import java.math.BigDecimal

data class ConsumerPlacedOrderRequest(
    val cost: BigDecimal
)
