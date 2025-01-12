package com.sinaglife.shoping_cart.cart.domain.events

import com.sinaglife.shoping_cart.cart.domain.cart_item.CartItemPrimitives
import com.sinaglife.shoping_cart.shared.domain.bus.event.DomainEvent
import java.time.LocalDateTime

data class CartUpdatedDomainEvent(
    val id: String,
    val items: List<CartItemPrimitives>,
    val subTotal: Double,
    val total: Double,
    val discount: Double?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val customerId: String?,
    val action: String
) : DomainEvent(aggregateId = id, "cart.updated") {
}