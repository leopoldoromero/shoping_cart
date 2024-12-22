package com.sinaglife.shoping_cart.cart.domain

class CartAmount(var value: Int) {
    fun increment(amount: Int) {
        value += amount;
    }

    fun decrement(amount: Int) {
        value -= amount;
    }
}