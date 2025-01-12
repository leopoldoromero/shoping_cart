package com.sinaglife.shoping_cart.cart.domain.cart_discount


data class CartDiscountPrimitives (
    val id: String,
    val amount: Double,
    val individualUse: Boolean,
    val code: String,
)
class CartDiscount(
    val id: CartDiscountId,
    val amount: CartDiscountAmount,
    val individualUse: Boolean,
    val code: CartDiscountCode,
) {
    companion object {
        fun fromPrimitives(
            props: CartDiscountPrimitives,
        ): CartDiscount {
            return CartDiscount(
                CartDiscountId.fromString(props.id),
                CartDiscountAmount(props.amount),
                props.individualUse,
                CartDiscountCode(props.code),
            )
        }
    }

    fun toPrimitives(): CartDiscountPrimitives {
        return CartDiscountPrimitives(
            id.toString(),
            amount.value,
            individualUse,
            code.value
        )
    }
}