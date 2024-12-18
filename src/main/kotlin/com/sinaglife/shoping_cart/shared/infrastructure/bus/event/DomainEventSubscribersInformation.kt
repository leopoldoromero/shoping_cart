package com.sinaglife.shoping_cart.shared.infrastructure.bus.event

import com.sinaglife.shoping_cart.shared.domain.bus.event.DomainEventSubscriber
import org.springframework.stereotype.Service
import org.reflections.Reflections

@Service
class DomainEventSubscribersInformation(
    var information: HashMap<Class<*>, DomainEventSubscriberInformation> = scanDomainEventSubscribers()
) {
    fun all(): Collection<DomainEventSubscriberInformation> {
        return information.values
    }

    fun rabbitMqFormattedNames(): Array<String> {
        val formattedNames = HashSet<String>()
        for (subscriberInformation in information.values) {
            formattedNames.add(subscriberInformation.formatRabbitMqQueueName())
        }
        return formattedNames.toTypedArray()
    }

    companion object {
        private fun scanDomainEventSubscribers(): HashMap<Class<*>, DomainEventSubscriberInformation> {
            val reflections = Reflections("com.sinaglife")
            val subscribers: Set<Class<*>> = reflections.getTypesAnnotatedWith(
                DomainEventSubscriber::class.java
            )

            val subscribersInformation = HashMap<Class<*>, DomainEventSubscriberInformation>()

            for (subscriberClass in subscribers) {
                val annotation = subscriberClass.getAnnotation(
                    DomainEventSubscriber::class.java
                )

                subscribersInformation[subscriberClass] =
                    DomainEventSubscriberInformation(subscriberClass, annotation.value.map { it.java })
            }

            return subscribersInformation
        }
    }
}
