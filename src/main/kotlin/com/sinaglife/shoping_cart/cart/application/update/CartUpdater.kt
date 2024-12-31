package com.sinaglife.shoping_cart.cart.application.update

import com.sinaglife.shoping_cart.cart.domain.CartId
import com.sinaglife.shoping_cart.cart.domain.CartRepository
import com.sinaglife.shoping_cart.cart.domain.CartUpdateAction
import com.sinaglife.shoping_cart.cart.domain.cart_item.*
import com.sinaglife.shoping_cart.cart.domain.errors.UnsupportedCartUpdateActionError
import com.sinaglife.shoping_cart.shared.errors.CartDoesNotExistError
import com.sinaglife.shoping_cart.shared.domain.bus.event.EventBus
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class CartUpdater(
    @Qualifier("CartMysqlRepository")
    private val repository: CartRepository,
    @Qualifier("SpringApplicationEventBus")
    private val eventBus: EventBus
) {
    fun execute(id: String, item: CartItemPrimitives, action: CartUpdateAction) {
        val cartOrNull = repository.find(CartId.fromString(id)) ?: throw CartDoesNotExistError()
        val validItem = CartItem.create(
            id = CartItemId.fromString(item.id),
            quantity = CartItemQty(item.quantity),
            price = CartItemPrice(item.price)
        )
        cartOrNull.updateItem(validItem, action)

        if (cartOrNull.isEmpty()) {
            TODO("Pending to implement delete opp")
        }
        repository.updateOne(cartOrNull)
        eventBus.publish(cartOrNull.pullDomainEvents())
    }
}