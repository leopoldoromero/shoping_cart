package com.sinaglife.shoping_cart.shared.domain.bus.command

class CommandHandlerExecutionError(cause: Throwable): RuntimeException(cause) {
}