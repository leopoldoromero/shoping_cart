package com.sinaglife.shoping_cart.cart.domain

import com.sinaglife.shoping_cart.cart.domain.cart_item.*
import com.sinaglife.shoping_cart.cart.domain.cart_discount.CartDiscount
import com.sinaglife.shoping_cart.cart.domain.cart_discount.CartDiscountPrimitives
import com.sinaglife.shoping_cart.cart.domain.cart_discount.CartDiscountTypes
import com.sinaglife.shoping_cart.cart.domain.events.CartCreatedDomainEvent
import com.sinaglife.shoping_cart.shared.domain.AggregateRoot
import com.sinaglife.shoping_cart.shared.domain.Guard
import java.time.LocalDateTime

data class CartPrimitives(
    val id: String,
    val items: MutableList<CartItemPrimitives>,
    val discount: CartDiscountPrimitives?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val customerId: String?,
)

class Cart private constructor(
    val id: CartId,
    val items: MutableList<CartItem>,
    val discount: CartDiscount?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val customerId: CartCustomerId?,
) : AggregateRoot() {
    companion object {
        fun create(
            id: CartId?,
            items: MutableList<CartItem>,
            discount: CartDiscount?,
            customerId: CartCustomerId?
        ): Cart {
            Guard.validateAgainstNullOrUndefined(
                mapOf(
                    "items" to items,
                )
            )
            val cart = Cart(
                id = id ?: CartId.random(),
                items,
                discount,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                customerId,
            )
            cart.record(CartCreatedDomainEvent(
                id = cart.id.value.toString(),
                items = cart.items.map { item -> item.toPrimitives() },
                discount = cart.discount?.let { it -> it.toPrimitives() },
                createdAt = cart.createdAt,
                updatedAt = cart.updatedAt,
                customerId = cart.customerId?.let { it -> it.value.toString() }
            ))
            return cart
        }

        fun fromPrimitives(
            props: CartPrimitives,
        ): Cart {
            return Cart(
                CartId.fromString(props.id),
                props.items.map { item -> CartItem.fromPrimitives(item) }.toMutableList(),
                props.discount?.let { CartDiscount.fromPrimitives(it) },
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

    fun subTotal(): Int {
        return items.sumOf { it -> it.total().value }
    }

    fun discountAmount(): Int {
        return discount?.let {
            when (it.type.value) {
                CartDiscountTypes.FIXED -> it.amount.value
                CartDiscountTypes.PERCENT -> (subTotal() * it.amount.value / 100)
            }
        } ?: 0
    }

    fun total(): Int {
        return subTotal() - discountAmount()
    }

}