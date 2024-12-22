package com.sinaglife.shoping_cart.cart.domain.discount

class DiscountAmount(val value: Int) {
    init {
        require(value > 0) { "Discount amount must be greater than 0" }
    }
}