package com.sinaglife.shoping_cart.cart.domain

import java.util.*

class CartCustomerId(val value: UUID) {
    companion object {
        fun fromString(value: String) = try {
            CartCustomerId(UUID.fromString(value))
        } catch (e: Exception) {
            throw InvalidIdException(value, e)
        }

        fun random(): CartCustomerId {
            return CartCustomerId(UUID.randomUUID())
        }
    }
}