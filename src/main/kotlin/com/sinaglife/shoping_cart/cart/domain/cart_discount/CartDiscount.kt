package com.sinaglife.shoping_cart.cart.domain.cart_discount


data class CartDiscountPrimitives (
    val id: String,
    val amount: Int,
    val type: String,
    val individualUse: Boolean,
    val code: String,
)
class CartDiscount(
    val id: CartDiscountId,
    val amount: CartDiscountAmount,
    val type: CartDiscountType,
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
                CartDiscountType.fromValue(props.type),
                props.individualUse,
                CartDiscountCode(props.code),
            )
        }
    }

    fun toPrimitives(): CartDiscountPrimitives {
        return CartDiscountPrimitives(
            id.toString(),
            amount.value,
            type.value.toString(),
            individualUse,
            code.value
        )
    }
}