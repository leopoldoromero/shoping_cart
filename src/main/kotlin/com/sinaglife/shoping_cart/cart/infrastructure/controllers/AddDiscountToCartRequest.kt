package com.sinaglife.shoping_cart.cart.infrastructure.controllers

data class AddDiscountToCartRequest(
    val cartId: String,
    val code: String,
)
