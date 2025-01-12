package com.sinaglife.shoping_cart.cart.application.delete

import com.sinaglife.shoping_cart.cart.domain.CartId
import com.sinaglife.shoping_cart.cart.domain.CartRepository
import com.sinaglife.shoping_cart.shared.domain.bus.event.EventBus
import com.sinaglife.shoping_cart.shared.errors.CartDoesNotExistError
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class CartDeleter(
    @Qualifier("CartMysqlRepository")
    private val repository: CartRepository,
    @Qualifier("SpringApplicationEventBus")
    private val eventBus: EventBus
) {
    fun execute(id: String) {
        val cartId = CartId.fromString(id)
        val cartOrNull = repository.find(cartId) ?: throw CartDoesNotExistError()
        cartOrNull.remove()
        repository.deleteCart(cartId)
        eventBus.publish(cartOrNull.pullDomainEvents())
    }
}