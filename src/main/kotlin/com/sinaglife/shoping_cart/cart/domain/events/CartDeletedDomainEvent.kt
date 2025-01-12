package com.sinaglife.shoping_cart.cart.domain.events

import com.sinaglife.shoping_cart.shared.domain.bus.event.DomainEvent

data class CartDeletedDomainEvent(
    val id: String
) : DomainEvent(aggregateId = id, "cart.deleted") {
}