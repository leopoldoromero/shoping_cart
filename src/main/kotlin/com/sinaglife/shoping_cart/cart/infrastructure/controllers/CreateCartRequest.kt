package com.sinaglife.shoping_cart.cart.infrastructure.controllers

data class CreateCartItemRequest(
    val id: String,
    val quantity: Int,
    val price: Double,
)
data class CreateCartRequest(
    val id: String,
    val items: List<CreateCartItemRequest>,
    val customerId: String?
)