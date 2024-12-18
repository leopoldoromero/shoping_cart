package com.sinaglife.shoping_cart.shared.domain.bus.event

interface EventBus {
    fun publish(events: List<DomainEvent>)
}