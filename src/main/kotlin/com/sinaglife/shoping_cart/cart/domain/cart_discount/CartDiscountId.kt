package com.sinaglife.shoping_cart.cart.domain.cart_discount

import com.sinaglife.shoping_cart.cart.domain.InvalidIdException
import java.util.*

class CartDiscountId(val value: UUID) {
    companion object {
        fun fromString(value: String) = try {
            CartDiscountId(UUID.fromString(value))
        } catch (e: Exception) {
            throw InvalidIdException(value, e)
        }

        fun random(): CartDiscountId {
            return CartDiscountId(UUID.randomUUID())
        }
    }
}