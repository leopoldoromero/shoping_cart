package com.sinaglife.shoping_cart.shared.domain.bus.query

class QueryHandlerExecutionError(cause: Throwable): RuntimeException(cause) {
}