package com.sinaglife.shoping_cart.cart.infrastructure.persistance.mongodb

import com.sinaglife.shoping_cart.cart.domain.cart_item.CartItemPrimitives
import com.sinaglife.shoping_cart.cart.domain.discount.DiscountPrimitives
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
class CartDbSchema(
    val id: String,
    val items: List<CartItemPrimitives>,
    val discount: DiscountPrimitives?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val customerId: String?
)