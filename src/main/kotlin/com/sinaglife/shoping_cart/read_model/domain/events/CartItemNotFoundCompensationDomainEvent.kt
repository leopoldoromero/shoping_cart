package com.sinaglife.shoping_cart.read_model.domain.events

import com.sinaglife.shoping_cart.shared.domain.bus.event.DomainEvent

data class CartItemNotFoundCompensationDomainEvent(
    val id: String,
    val cartId: String,
    val failedProductIds: List<String>,
) : DomainEvent(aggregateId = id, "cart.item-not-found-compensation") {
}