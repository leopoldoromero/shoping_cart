package com.sinaglife.shoping_cart.shared.domain

import com.sinaglife.shoping_cart.shared.domain.bus.event.DomainEvent

open class AggregateRoot {
    private var domainEvents:  MutableList<DomainEvent> = mutableListOf()

    fun pullDomainEvents(): List<DomainEvent> {
        val events: List<DomainEvent> = ArrayList(domainEvents)
        println("Publishing events:::")
        domainEvents.clear()

        return events
    }

    protected fun record(event: DomainEvent) {
        println("Record event::: $event")
        domainEvents.add(event)
    }
}
