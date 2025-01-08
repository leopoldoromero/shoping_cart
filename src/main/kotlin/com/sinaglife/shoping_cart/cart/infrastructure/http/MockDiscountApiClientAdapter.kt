package com.sinaglife.shoping_cart.cart.infrastructure.http

import com.sinaglife.shoping_cart.cart.domain.DiscountApiClient
import com.sinaglife.shoping_cart.cart.domain.cart_discount.CartDiscount
import com.sinaglife.shoping_cart.cart.domain.cart_discount.CartDiscountPrimitives

class MockDiscountApiClientAdapter() : DiscountApiClient {
    override fun getDiscount(code: String, subTotal: Int, customerId: String?): CartDiscount {
        if (code != "TEST2024") {
            throw Exception("Invalid coupon")
        }

        val discountPrimitive = CartDiscountPrimitives(
            id = "40676af1-09b2-49da-9665-8d9901ae9bf2",
            code = code,
            type = "FIXED",
            amount = 10,
            individualUse = true,
        )
        return CartDiscount.fromPrimitives(
            props = discountPrimitive
        )
    }

    private fun calculateAmount(subTotal: Int, type: String, amount: Int): Int {
        return when(type) {
            "FIXED" -> subTotal - amount
            "PERCENT" -> subTotal * (1 + amount)
            else -> throw Exception("Invalid calculation")
        }
    }
}