package com.sinaglife.shoping_cart.shared.domain.bus.event
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@MustBeDocumented
annotation class DomainEventSubscriber(val value: Array<KClass<out DomainEvent>>)