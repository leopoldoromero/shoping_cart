package com.sinaglife.shoping_cart.read_model.domain

import java.time.LocalDateTime

data class CartReadModel(
    val id: String,
    val items: List<CartItemReadModel>,
    val subTotal: Double,
    val total: Double,
    val discount: Double?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val customerId: String?
)