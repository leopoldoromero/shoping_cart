package com.sinaglife.shoping_cart.shared.domain.bus.query

import kotlin.reflect.KClass

class QueryNotRegisteredError(query: KClass<out Query>): Exception("The query ${query.toString()} hasn't a query handler associated") {
}