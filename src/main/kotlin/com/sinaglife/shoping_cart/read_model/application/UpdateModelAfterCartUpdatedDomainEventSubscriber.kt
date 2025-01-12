package com.sinaglife.shoping_cart.read_model.application

import com.sinaglife.shoping_cart.cart.domain.events.CartUpdatedDomainEvent
import com.sinaglife.shoping_cart.read_model.domain.*
import com.sinaglife.shoping_cart.read_model.domain.errors.ProductDoesNotExistError
import com.sinaglife.shoping_cart.read_model.domain.events.CartItemNotFoundCompensationDomainEvent
import com.sinaglife.shoping_cart.shared.domain.AppLogger
import com.sinaglife.shoping_cart.shared.domain.LogInput
import com.sinaglife.shoping_cart.shared.domain.bus.event.DomainEventSubscriber
import com.sinaglife.shoping_cart.shared.domain.bus.event.EventBus
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
@DomainEventSubscriber([CartUpdatedDomainEvent::class])
class UpdateModelAfterCartUpdatedDomainEventSubscriber(
    private val productApiClient: ProductApiClient,
    @Qualifier("CartReadModelMongoRepository")
    private val repository: CartReadRepository,
    @Qualifier("SpringApplicationEventBus")
    private val eventBus: EventBus,
    private val loggerFactory: (Class<*>) -> AppLogger
) {
    private val logger = loggerFactory(UpdateModelAfterCartUpdatedDomainEventSubscriber::class.java)
    @EventListener
    fun on(event: CartUpdatedDomainEvent) {
        logger.log(LogInput(methodName = "on", input = event, message = "Processing cart updated event"))

        val failedProductIds = mutableListOf<String>()
        val cartItems = event.items.mapNotNull { item ->
            try {
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
            } catch (ex: ProductDoesNotExistError) {
                failedProductIds.add(item.id)
                null
            }
        }

        if (failedProductIds.isNotEmpty()) {
            eventBus.publish(listOf(CartItemNotFoundCompensationDomainEvent(
                id = event.id,
                cartId = event.id,
                failedProductIds = failedProductIds,
            )))
        } else {
            repository.save(
                CartReadModel(
                    id = event.id,
                    items = cartItems,
                    discount = event.discount,
                    subTotal = event.subTotal,
                    total = event.total,
                    createdAt = event.createdAt,
                    updatedAt = event.updatedAt,
                    customerId = event.customerId,
                )
            )
        }
    }
}