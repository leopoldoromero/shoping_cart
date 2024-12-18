package com.sinaglife.shoping_cart.shared.infrastructure.bus.event

import com.sinaglife.shoping_cart.shared.domain.Utils
import com.sinaglife.shoping_cart.shared.domain.bus.event.DomainEvent


class DomainEventSubscriberInformation(
    private val subscriberClass: Class<*>,
    private val subscribedEvents: List<Class<out DomainEvent?>>
) {
    fun subscriberClass(): Class<*> {
        return subscriberClass
    }

    fun subscribedEvents(): List<Class<out DomainEvent?>> {
        return subscribedEvents
    }

    private fun contextName(): String {
        val nameParts = subscriberClass.name.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()

        return nameParts[2]
    }

    private fun moduleName(): String {
        val nameParts = subscriberClass.name.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()

        return nameParts[3]
    }

    private fun className(): String {
        val nameParts = subscriberClass.name.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()

        return nameParts[nameParts.size - 1]
    }

    fun formatRabbitMqQueueName(): String {
        return java.lang.String.format(
            "application.%s.%s.%s", contextName(), moduleName(), Utils.toSnake(className())
        )
    }
}
