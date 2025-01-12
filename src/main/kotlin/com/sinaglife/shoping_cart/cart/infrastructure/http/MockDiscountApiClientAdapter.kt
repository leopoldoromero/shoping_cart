package com.sinaglife.shoping_cart.cart.infrastructure.http

import com.sinaglife.shoping_cart.cart.domain.DiscountApiClient
import com.sinaglife.shoping_cart.cart.domain.cart_discount.CartDiscount
import com.sinaglife.shoping_cart.cart.domain.cart_discount.CartDiscountPrimitives

class MockDiscountApiClientAdapter() : DiscountApiClient {
    override fun getDiscount(code: String, subTotal: Double, customerId: String?): CartDiscount {
        if (code != "FIXED2024" || code !== "PERCENT2024") {
            throw Exception("Invalid coupon")
        }

        val discountAmount: Double = when(code) {
            "FIXED2024" -> 10.00
            "PERCENT2024" -> subTotal * 0.1
            else -> throw Exception("Unhandled error")
        }

        val discountPrimitive = CartDiscountPrimitives(
            id = "40676af1-09b2-49da-9665-8d9901ae9bf2",
            code = code,
            amount = discountAmount,
            individualUse = true,
        )
        return CartDiscount.fromPrimitives(
            props = discountPrimitive
        )
    }
}