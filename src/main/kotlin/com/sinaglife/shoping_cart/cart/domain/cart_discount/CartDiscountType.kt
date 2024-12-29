package com.sinaglife.shoping_cart.cart.domain.cart_discount

import com.sinaglife.shoping_cart.shared.domain.EnumValueObject

enum class CartDiscountTypes(val type: String) {
    FIXED("fixed"),
    PERCENT("percent");
}

class CartDiscountType private constructor(type: CartDiscountTypes) : EnumValueObject<CartDiscountTypes>(
    value = type,
    validValues = CartDiscountTypes.values().toList()
) {
    companion object {
        fun fromValue(value: String): CartDiscountType {
            return when (value) {
                CartDiscountTypes.FIXED.type -> CartDiscountType(CartDiscountTypes.FIXED)
                CartDiscountTypes.PERCENT.type -> CartDiscountType(CartDiscountTypes.PERCENT)
                else -> throw IllegalArgumentException("The discount type $value is invalid")
            }
        }
    }

    override fun throwErrorForInvalidValue(value: CartDiscountTypes) {
        throw IllegalArgumentException("Invalid discount type: $value")
    }
}

