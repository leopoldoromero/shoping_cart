package com.sinaglife.shoping_cart.cart.domain

import com.sinaglife.shoping_cart.cart.domain.cart_item.*
import com.sinaglife.shoping_cart.cart.domain.discount.Discount
import com.sinaglife.shoping_cart.cart.domain.discount.DiscountPrimitives
import com.sinaglife.shoping_cart.shared.domain.Guard
import java.time.LocalDateTime

data class CartPrimitives(
    val id: String,
    val items: MutableList<CartItemPrimitives>,
    val discount: DiscountPrimitives?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val customerId: String?,
)

class Cart private constructor(
    val id: CartId,
    val items: MutableList<CartItem>,
    val discount: Discount?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val customerId: CartCustomerId?,
) {
    companion object {
        fun create(
            id: CartId?,
            items: MutableList<CartItem>,
            discount: Discount,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime,
            customerId: CartCustomerId?
        ): Cart {
            Guard.validateAgainstNullOrUndefined(
                mapOf(
                    "items" to items,
                )
            )
            return Cart(
                id = id ?: CartId.random(),
                items,
                discount,
                createdAt,
                updatedAt,
                customerId,
            )
        }

        fun fromPrimitives(
            props: CartPrimitives,
        ): Cart {
            return Cart(
                CartId.fromString(props.id),
                props.items.map { item -> CartItem.fromPrimitives(item) }.toMutableList(),
                props.discount?.let { Discount.fromPrimitives(it) },
                props.createdAt,
                props.updatedAt,
                props.customerId?.let { CartCustomerId.fromString(it) }
            )
        }
    }

    fun toPrimitives(): CartPrimitives {
        return CartPrimitives(
            id.value.toString(),
            items.map { item -> item.toPrimitives() }.toMutableList(),
            discount?.let { it.toPrimitives() },
            createdAt,
            updatedAt,
            customerId?.let { it.toString() }
        )
    }

}