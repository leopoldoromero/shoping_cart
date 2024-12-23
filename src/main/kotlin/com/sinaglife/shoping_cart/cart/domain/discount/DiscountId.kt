package com.sinaglife.shoping_cart.cart.domain.discount

import com.sinaglife.shoping_cart.cart.domain.InvalidIdException
import com.sinaglife.shoping_cart.cart.domain.cart_item.CartItemId
import java.util.*

class DiscountId(val value: UUID) {
    companion object {
        fun fromString(value: String) = try {
            DiscountId(UUID.fromString(value))
        } catch (e: Exception) {
            throw InvalidIdException(value, e)
        }

        fun random(): DiscountId {
            return DiscountId(UUID.randomUUID())
        }
    }
}