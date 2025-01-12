package com.sinaglife.shoping_cart.cart.domain.cart_item

import com.sinaglife.shoping_cart.shared.domain.Guard

data class CartItemPrimitives(
    val id: String,
    val quantity: Int,
    val price: Double,
)

class CartItem private constructor(
    val id: CartItemId,
    val quantity: CartItemQty,
    val price: CartItemPrice,
){
    companion object {
        fun create(
            id: CartItemId?,
            quantity: CartItemQty,
            price: CartItemPrice,
        ): CartItem {
            Guard.validateAgainstNullOrUndefined(
                mapOf(
                    "quantity" to quantity,
                    "price" to price,
                )
            )
            return CartItem(
                id = id ?: CartItemId.random(),
                quantity,
                price,
            )
        }

        fun fromPrimitives(
            props: CartItemPrimitives,
        ): CartItem {
            return CartItem(
                CartItemId.fromString(props.id),
                CartItemQty(props.quantity),
                CartItemPrice(props.price),
            )
        }
    }

    fun toPrimitives(): CartItemPrimitives {
        return CartItemPrimitives(
            id.value.toString(),
            quantity.value,
            price.value,

        )
    }

    fun total(): CartItemTotal {
        return CartItemTotal(price.value * quantity.value)
    }
}