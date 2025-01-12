package com.sinaglife.shoping_cart.cart.application.update

import com.sinaglife.shoping_cart.cart.domain.CartId
import com.sinaglife.shoping_cart.cart.domain.CartRepository
import com.sinaglife.shoping_cart.cart.domain.CartUpdateAction
import com.sinaglife.shoping_cart.cart.domain.cart_item.CartItemId
import com.sinaglife.shoping_cart.cart.domain.events.CartUpdatedDomainEvent
import com.sinaglife.shoping_cart.read_model.domain.events.CartItemNotFoundCompensationDomainEvent
import com.sinaglife.shoping_cart.shared.domain.bus.event.DomainEventSubscriber
import com.sinaglife.shoping_cart.shared.domain.bus.event.EventBus
import com.sinaglife.shoping_cart.shared.errors.CartDoesNotExistError
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
@DomainEventSubscriber([CartItemNotFoundCompensationDomainEvent::class])
class CartItemNotFoundCompensationDomainEventSubscriber(
    @Qualifier("CartMysqlRepository")
    private val repository: CartRepository,
    @Qualifier("SpringApplicationEventBus")
    private val eventBus: EventBus
) {
    @EventListener
    fun on(event: CartItemNotFoundCompensationDomainEvent) {
        val cartOrNull = repository.find(CartId.fromString(event.cartId)) ?: throw CartDoesNotExistError()
        event.failedProductIds.forEach { id ->
            cartOrNull.items.find { it.id.equals(CartItemId.fromString(id)) }?.let { cartItem ->
                cartOrNull.updateItem(cartItem, CartUpdateAction.REMOVE)
            } ?: print("Item  not found into items list")
        }
        repository.updateOne(cartOrNull)
        eventBus.publish(cartOrNull.pullDomainEvents())
    }
}