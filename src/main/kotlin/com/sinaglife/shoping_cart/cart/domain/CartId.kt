package com.sinaglife.shoping_cart.cart.domain

import java.util.*

class CartId(val value: UUID) {
    companion object {
        fun fromString(value: String) = try {
            CartId(UUID.fromString(value))
        } catch (e: Exception) {
            throw InvalidIdException(value, e)
        }

        fun random(): CartId {
            return CartId(UUID.randomUUID())
        }
    }
}