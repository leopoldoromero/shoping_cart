package com.sinaglife.shoping_cart.read_model.application

import com.sinaglife.shoping_cart.cart.domain.events.CartCreatedDomainEvent
import com.sinaglife.shoping_cart.read_model.domain.*
import com.sinaglife.shoping_cart.shared.domain.bus.event.DomainEventSubscriber
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
@DomainEventSubscriber([CartCreatedDomainEvent::class])
class CartReadModelProjector(
    private val productApiClient: ProductApiClient,
    @Qualifier("CartReadModelMongoRepository")
    private val repository: CartReadRepository,
) {
    @EventListener
    fun on(event: CartCreatedDomainEvent) {
        val items = event.items
        val cartItems: List<CartItemReadModel> = items.map { item ->
            val dto = productApiClient.getProductDetails(item.id)
            CartItemReadModel(
                id = dto.id,
                name = dto.name,
                code = dto.code,
                stock = dto.stock,
                price = item.price,
                quantity = item.quantity,
                image = CartItemImgReadModel(
                    name = dto.images[0].name,
                    alt = dto.images[0].alt,
                    src = dto.images[0].src
                )
            )
        }

        repository.save(CartReadModel(
            id = event.id,
            items = cartItems,
            discount = event.discount,
            subTotal = event.subTotal,
            total = event.total,
            createdAt = event.createdAt,
            updatedAt = event.updatedAt,
            customerId = event.customerId,
        ))
    }
}