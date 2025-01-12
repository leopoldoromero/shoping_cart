package com.sinaglife.shoping_cart.cart.domain

import com.sinaglife.shoping_cart.cart.domain.cart_discount.CartDiscount

interface DiscountApiClient {
    fun getDiscount(
        code: String,
        subTotal: Double,
        customerId: String?
    ): CartDiscount
}