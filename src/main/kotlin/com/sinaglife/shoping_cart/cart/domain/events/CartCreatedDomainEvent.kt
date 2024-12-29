package com.sinaglife.shoping_cart.cart.domain.events

import com.sinaglife.shoping_cart.cart.domain.cart_item.CartItemPrimitives
import com.sinaglife.shoping_cart.cart.domain.cart_discount.CartDiscountPrimitives
import com.sinaglife.shoping_cart.shared.domain.bus.event.DomainEvent
import java.time.LocalDateTime

class CartCreatedDomainEvent(
    val id: String,
    val items: List<CartItemPrimitives>,
    val subTotal: Int,
    val total: Int,
    val discount: Int?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val customerId: String?,
) : DomainEvent(aggregateId = id, "cart.created") {
}