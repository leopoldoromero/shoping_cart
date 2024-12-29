package com.sinaglife.shoping_cart.cart.domain.cart_discount

class CartDiscountAmount(val value: Int) {
    init {
        require(value > 0) { "Discount amount must be greater than 0" }
    }
}