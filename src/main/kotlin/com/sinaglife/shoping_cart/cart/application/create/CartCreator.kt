package com.sinaglife.shoping_cart.cart.application.create

import com.sinaglife.shoping_cart.cart.domain.Cart
import com.sinaglife.shoping_cart.cart.domain.CartCustomerId
import com.sinaglife.shoping_cart.cart.domain.CartId
import com.sinaglife.shoping_cart.cart.domain.CartRepository
import com.sinaglife.shoping_cart.cart.domain.cart_item.CartItem
import com.sinaglife.shoping_cart.cart.domain.cart_item.CartItemPrimitives
import com.sinaglife.shoping_cart.shared.domain.bus.event.EventBus
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class CartCreator(
    @Qualifier("CartMysqlRepository")
    private val repository: CartRepository,
    @Qualifier("SpringApplicationEventBus")
    private val eventBus: EventBus
) {
    fun execute(
        id: String,
        items: List<CartItemPrimitives>,
        customerId: String?,
    ) {
        val cart = Cart.create(
            id = CartId.fromString(id),
            items = items.map { item -> CartItem.fromPrimitives(item) }.toMutableList(),
            discount = null,
            customerId = customerId?.let { it -> CartCustomerId.fromString(customerId) }
        )
        repository.save(cart)
        eventBus.publish(cart.pullDomainEvents())
    }
}