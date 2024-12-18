package com.sinaglife.shoping_cart.shared.domain.bus.event

import java.time.LocalDateTime
import java.util.*

open class DomainEvent(val aggregateId: String, val eventName: String) {
    private val id = UUID.randomUUID()
    private val occurredOn = LocalDateTime.now()
}