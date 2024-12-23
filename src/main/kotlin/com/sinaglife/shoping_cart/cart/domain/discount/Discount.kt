package com.sinaglife.shoping_cart.cart.domain.discount


data class DiscountPrimitives (
    val id: String,
    val amount: Int,
    val type: String,
    val individualUse: Boolean,
    val code: String,
)
class Discount(
    val id: DiscountId,
    val amount: DiscountAmount,
    val type: DiscountType,
    val individualUse: Boolean,
    val code: DiscountCode,
) {
    companion object {
        fun fromPrimitives(
            props: DiscountPrimitives,
        ): Discount {
            return Discount(
                DiscountId.fromString(props.id),
                DiscountAmount(props.amount),
                DiscountType.fromValue(props.type),
                props.individualUse,
                DiscountCode(props.code),
            )
        }
    }

    fun toPrimitives(): DiscountPrimitives {
        return DiscountPrimitives(
            id.toString(),
            amount.value,
            type.value.toString(),
            individualUse,
            code.value
        )
    }
}