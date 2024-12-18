package com.sinaglife.shoping_cart.shared.infrastructure.bus.event.spring
import com.sinaglife.shoping_cart.shared.domain.bus.event.DomainEvent
import com.sinaglife.shoping_cart.shared.domain.bus.event.EventBus
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service(value = "SpringApplicationEventBus")
class SpringApplicationEventBus(private val publisher: ApplicationEventPublisher) : EventBus {
    @Async
    override fun publish(events: List<DomainEvent>) {
        println("Publishing events from SpringApplicationEventBus :::: ${events.size}")
        events.forEach {
            println("Event::::${it.javaClass}")
            publisher.publishEvent(it)
        }
    }
}
