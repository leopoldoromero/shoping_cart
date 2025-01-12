package com.sinaglife.shoping_cart.cart.infrastructure.controllers

import com.sinaglife.shoping_cart.cart.domain.CartUpdateAction
import com.sinaglife.shoping_cart.cart.domain.cart_item.CartItemPrimitives

data class UpdateCartItemRequest(
    val id: String,
    val quantity: Int,
    val price: Double
)
data class UpdateCartRequest(
    val id: String,
    val item: UpdateCartItemRequest,
    val action: CartUpdateAction
)