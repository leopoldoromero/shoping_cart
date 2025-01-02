package com.sinaglife.shoping_cart.read_model.application

import com.sinaglife.shoping_cart.cart.domain.events.CartDeletedDomainEvent
import com.sinaglife.shoping_cart.read_model.domain.CartReadRepository
import com.sinaglife.shoping_cart.shared.domain.AppLogger
import com.sinaglife.shoping_cart.shared.domain.LogInput
import com.sinaglife.shoping_cart.shared.domain.bus.event.DomainEventSubscriber
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
@DomainEventSubscriber([CartDeletedDomainEvent::class])
class DeleteAfterCartDeletedDomainEventSubscriber(
    @Qualifier("CartReadModelMongoRepository")
    private val repository: CartReadRepository,
    private val loggerFactory: (Class<*>) -> AppLogger
) {
    private val logger = loggerFactory(DeleteAfterCartDeletedDomainEventSubscriber::class.java)
    @EventListener
    fun on(event: CartDeletedDomainEvent) {
        try {
            logger.log(LogInput(methodName = "on", input = event, message = "Processing cart deleted event"))
            repository.deleteCart(event.id)
        } catch (e: Exception) {
            logger.error(LogInput(methodName = "on", message = "Error processing cart deleted event", error = e))
        }
    }
}