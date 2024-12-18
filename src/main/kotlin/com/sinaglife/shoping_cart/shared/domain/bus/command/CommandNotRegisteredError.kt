package com.sinaglife.shoping_cart.shared.domain.bus.command

import kotlin.reflect.KClass

class CommandNotRegisteredError(command: KClass<out Command>): Exception("The command ${command.toString()} hasn't a command handler associated") {
}