package com.sinaglife.shoping_cart.cart.domain.discount

import com.sinaglife.shoping_cart.shared.domain.EnumValueObject

enum class DiscountTypes(val type: String) {
    FIXED("fixed"),
    PERCENT("percent");
}

class DiscountType private constructor(type: DiscountTypes) : EnumValueObject<DiscountTypes>(
    value = type,
    validValues = DiscountTypes.values().toList()
) {
    companion object {
        fun fromValue(value: String): DiscountType {
            return when (value) {
                DiscountTypes.FIXED.type -> DiscountType(DiscountTypes.FIXED)
                DiscountTypes.PERCENT.type -> DiscountType(DiscountTypes.PERCENT)
                else -> throw IllegalArgumentException("The discount type $value is invalid")
            }
        }
    }

    override fun throwErrorForInvalidValue(value: DiscountTypes) {
        throw IllegalArgumentException("Invalid discount type: $value")
    }
}

