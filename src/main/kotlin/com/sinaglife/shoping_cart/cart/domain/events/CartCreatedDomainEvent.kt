package com.sinaglife.shoping_cart.cart.domain.events

import com.sinaglife.shoping_cart.cart.domain.cart_item.CartItemPrimitives
import com.sinaglife.shoping_cart.cart.domain.discount.DiscountPrimitives
import com.sinaglife.shoping_cart.shared.domain.bus.event.DomainEvent
import java.time.LocalDateTime

class CartCreatedDomainEvent(
    val id: String,
    val items: List<CartItemPrimitives>,
    val discount: DiscountPrimitives?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val customerId: String?,
) : DomainEvent(aggregateId = id, "cart.created") {
}