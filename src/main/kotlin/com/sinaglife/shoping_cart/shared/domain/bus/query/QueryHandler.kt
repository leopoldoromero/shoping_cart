package com.sinaglife.shoping_cart.shared.domain.bus.query

interface QueryHandler<Q : Query, R : QueryResponse> {
    fun handle(query: Q): R
}