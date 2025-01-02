package com.sinaglife.shoping_cart.cart.domain

import com.sinaglife.shoping_cart.cart.domain.cart_item.*
import com.sinaglife.shoping_cart.cart.domain.cart_discount.CartDiscount
import com.sinaglife.shoping_cart.cart.domain.cart_discount.CartDiscountPrimitives
import com.sinaglife.shoping_cart.cart.domain.cart_discount.CartDiscountTypes
import com.sinaglife.shoping_cart.cart.domain.errors.UnsupportedCartUpdateActionError
import com.sinaglife.shoping_cart.cart.domain.events.CartCreatedDomainEvent
import com.sinaglife.shoping_cart.cart.domain.events.CartDeletedDomainEvent
import com.sinaglife.shoping_cart.cart.domain.events.CartUpdatedDomainEvent
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
                subTotal = cart.subTotal(),
                total = cart.total(),
                discount = cart.discountAmount(),
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

    fun addItem(item: CartItem) {
        val existingItem = items.find { it.id.equals(item.id) }

        if (existingItem == null) {
            items.add(item)
        } else {
            items.replaceAll { currentItem ->
                if (currentItem.id.equals(existingItem.id)) {
                    CartItem.create(
                        id = existingItem.id,
                        quantity = CartItemQty(existingItem.quantity.value + item.quantity.value),
                        price = existingItem.price
                    )
                } else {
                    currentItem
                }
            }
        }
    }

    fun removeItem(item: CartItem) {
        val existingItem = items.find { it.id.equals(item.id) }

        if (existingItem == null) {
            return
        }

        val currentQty = existingItem.quantity.value
        val quantityDiff = currentQty - item.quantity.value
        if (quantityDiff <= 0) {
            items.remove(item)
        } else {
            items.replaceAll { currentItem ->
                if (currentItem.id.equals(existingItem.id)) {
                    CartItem.create(
                        id = existingItem.id,
                        quantity = CartItemQty(existingItem.quantity.value - item.quantity.value),
                        price = existingItem.price
                    )
                } else {
                    currentItem
                }
            }
        }
    }

    fun updateItem(item: CartItem, action: CartUpdateAction) {
        when(action) {
            CartUpdateAction.ADD -> addItem(item)
            CartUpdateAction.REMOVE -> removeItem(item)
            else -> throw UnsupportedCartUpdateActionError(action.toString())
        }
        record(CartUpdatedDomainEvent(
            id = id.value.toString(),
            items = items.map { item -> item.toPrimitives() },
            subTotal = subTotal(),
            total = total(),
            discount = discountAmount(),
            createdAt = createdAt,
            updatedAt = updatedAt,
            customerId = customerId?.let { it -> it.value.toString() },
            action = action.toString()
        ))
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

    fun isEmpty(): Boolean {
        return items.isEmpty()
    }

    fun remove() {
        record(CartDeletedDomainEvent(id.value.toString()))
    }
}