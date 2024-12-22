package com.sinaglife.shoping_cart.cart.domain.cart_item

import com.sinaglife.shoping_cart.cart.domain.InvalidIdException
import java.util.*

class CartItemId(val value: UUID) {
    companion object {
        fun fromString(value: String) = try {
            CartItemId(UUID.fromString(value))
        } catch (e: Exception) {
            throw InvalidIdException(value, e)
        }

        fun random(): CartItemId {
            return CartItemId(UUID.randomUUID())
        }
    }
}