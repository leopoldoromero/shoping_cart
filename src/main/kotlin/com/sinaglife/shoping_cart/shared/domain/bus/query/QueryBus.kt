package com.sinaglife.shoping_cart.shared.domain.bus.query

interface QueryBus {
    fun ask(query: Query): QueryResponse
}